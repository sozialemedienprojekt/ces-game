package de.hub.cses.ces.jsf.bean.game;

/*
 * #%L
 * CES-Game
 * %%
 * Copyright (C) 2015 Humboldt-Universität zu Berlin,
 * Department of Computer Science,
 * Research Group "Computer Science Education / Computer Science and Society"
 * Sebastian Gross <sebastian.gross@hu-berlin.de>
 * Sven Strickroth <sven.strickroth@hu-berlin.de>
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
import de.hub.cses.ces.entity.client.User;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.Factory;
import de.hub.cses.ces.entity.company.accounting.Balance;
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.entity.company.cooperator.role.ManufacturerRole;
import de.hub.cses.ces.entity.company.cooperator.role.MarketerRole;
import de.hub.cses.ces.entity.company.cooperator.role.PurchaserRole;
import de.hub.cses.ces.entity.company.cooperator.role.Role;
import de.hub.cses.ces.entity.company.cooperator.role.SellerRole;
import de.hub.cses.ces.entity.company.warehouse.Stock;
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import de.hub.cses.ces.entity.economy.DynamicDemand;
import de.hub.cses.ces.entity.economy.DynamicSupply;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.product.IntermediateProduct;
import de.hub.cses.ces.entity.product.Part;
import de.hub.cses.ces.entity.product.Product;
import de.hub.cses.ces.entity.production.Production;
import de.hub.cses.ces.entity.production.ProductionPlan;
import de.hub.cses.ces.entity.text.SupportedLanguage;
import de.hub.cses.ces.event.Notification;
import de.hub.cses.ces.jsf.bean.util.RedirectionUtil;
import de.hub.cses.ces.service.observer.NotificationService;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.util.I18nTextUtil;
import de.hub.cses.ces.util.qualifier.Identify;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("PlayBean")
@ViewScoped
public class PlayBean implements Serializable {

    private Game game;
    private Long gameId;

    private Cooperator cooperator;
    private Long cooperatorId;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private GameFacade gameFacade;

    @Inject
    @Identify
    private User user;

    @Inject
    private NotificationService notificationObserver;

    @Inject
    private RedirectionUtil redirectionUtil;

    private boolean purchaseTab = false;
    private boolean productionTab = false;
    private boolean sellingTab = false;
    private boolean marketingTab = false;

    //Cavan
    //Functions to control alert buttons
        
    /*True if some production will be skipped tomorrow due to lack of raw materials*/
    public boolean getOutOfMaterialAlert() {
        //Get current stockpile
        List<Stock> stocks = this.getWarehouse().getStocks();
        //Get current production plan
        List<ProductionPlan> PPL = this.getProduction().getProductionPlans();
        
        //Calculate daily consumption of stock
        Map<IntermediateProduct, Double> usedDaily = new HashMap<>();
        
        for (ProductionPlan pp : PPL) 
            if (pp.getWorkforce() > 0){
                //calculate max. amount that could be produced
                double producedDaily = (cooperator.getCompany().getEconomy().getMarket().getWorkingHoursPerDay() * pp.getWorkforce()) / pp.getFinalProduct().getRequiredWorkingTime();
                
                //add to consumption
                for (Part p: pp.getFinalProduct().getPartsList().getParts())
                    usedDaily.put(p.getIntermediateProduct(), 
                            producedDaily * p.getQuantity() + 
                            usedDaily.getOrDefault(p.getIntermediateProduct(), 0.0));        
            }
        
        //Output it (debugging)
        for (IntermediateProduct p : usedDaily.keySet())
            System.out.println("Today Company " + cooperator.getCompany().getIdentifier() + " will use: " + usedDaily.get(p) + " " + p.getDescription().getText(SupportedLanguage.DEUTSCH));       
        
        //Check if Stores < Consumption
        for (Stock s : stocks){
            Product p = s.getProduct();
            if (usedDaily.getOrDefault(p, -1.0) > s.getQuantity())
                return true;
        }
        
        return false;   
    }

    /*True if balance is negative*/
    public boolean getNegBalanceAlert() {
        if (this.getBalance().getAmount() < 0)
             return true;
        else 
           return false;
    }

    /*True if there is idle core workforce*/
    public boolean getIdleWorkersAlert() {
        
        int coreWorkforce = this.getFactory().getCoreWorkforce();
        int busyWorkforce = 0;
        
        Collection<ProductionPlan> productionPlans = (List<ProductionPlan>) this.getProduction().getProductionPlans();
        if (productionPlans != null && !productionPlans.isEmpty()) {
            for (ProductionPlan productionPlan : productionPlans) {
                busyWorkforce += productionPlan.getWorkforce();
            }
        }
        
        if (coreWorkforce - busyWorkforce > 1)
                return true;
        else
            return false;
    }

    /*True if we have more than 15x the current max. demand for a finished good*/
    public boolean getFullStoresAlert(){
        return false;
    }
    
    /**
     *
     */
    public PlayBean() {

    }

    /**
     *
     */
    @PostConstruct
    public void init() {
    }

    /**
     *
     * @return
     */
    public Collection<Notification> getNotifications() {
        List<Notification> notifications = notificationObserver.getAll(gameId, cooperator.getCompany().getId(), cooperator.getId(), cooperator.getUser().getId());
        Collections.sort(notifications);
        return notifications;
    }

    /**
     *
     * @return
     */
    public Date getCurrentDate() {
        return game.getToday();
    }

    /**
     *
     * @param gameId
     */
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     *
     * @return
     */
    public Long getGameId() {
        return gameId;
    }

    /**
     *
     * @return
     */
    public GameStatus getGameStatus() {
        return game.getGameStatus();
    }

    /**
     *
     * @param cooperatorId
     */
    public void setCooperatorId(Long cooperatorId) {
        this.cooperatorId = cooperatorId;
    }

    private void initTabs() {
        try {
            Collection<Role> roles = cooperator.getRoles();
            roles.stream().map((Role role) -> role.getClass()).forEach((clazz) -> {
                if (PurchaserRole.class.isAssignableFrom(clazz)) {
                    purchaseTab = true;
                } else if (ManufacturerRole.class.isAssignableFrom(clazz)) {
                    productionTab = true;
                } else if (SellerRole.class.isAssignableFrom(clazz)) {
                    sellingTab = true;
                } else if (MarketerRole.class.isAssignableFrom(clazz)) {
                    marketingTab = true;
                }
            });
        } catch (NullPointerException npe) {
            logger.log(Level.INFO, null, npe);
        }
    }

    /**
     *
     * @return
     */
    public Long getCooperatorId() {
        return cooperatorId;
    }

    /**
     *
     * @return
     */
    public Cooperator getCooperator() {
        return cooperator;
    }

    /**
     *
     * @throws IOException
     */
    public void check() throws IOException {
        if (user == null) {
            redirectionUtil.redirect("index.xhtml");
            return;
        }
        if (gameId == null || cooperatorId == null) {
            redirectionUtil.redirect("index.xhtml");
            return;
        }
        game = gameFacade.find(gameId);
        if (game == null) {
            redirectionUtil.redirect("index.xhtml");
            return;
        }
        try {
            boolean found = false;
            for (Company company : game.getEconomy().getCompanies()) {
                for (Cooperator _cooperator : company.getCooperators()) {
                    if (cooperatorId.equals(_cooperator.getId())) {
                        found = true;
                        this.cooperator = _cooperator;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                redirectionUtil.redirect("index.xhtml");
                return;
            }
            if (!user.equals(this.cooperator.getUser())) {
                redirectionUtil.redirect("index.xhtml");
                return;
            }
        } catch (NullPointerException npe) {
            redirectionUtil.redirect("index.xhtml");
            return;
        }
        initTabs();
    }

    /**
     *
     * @return
     */
    public boolean renderPurchaseTab() {
        return purchaseTab;
    }

    /**
     *
     * @return
     */
    public boolean renderProductionTab() {
        return productionTab;
    }

    /**
     *
     * @return
     */
    public boolean renderSellingTab() {
        return sellingTab;
    }

    /**
     *
     * @return
     */
    public boolean renderMarketingTab() {
        return marketingTab;
    }

    /**
     *
     * @return
     */
    public Collection<DynamicSupply> getSupplies() {
        List<DynamicSupply> supplies = new ArrayList<>(game.getEconomy().getEconomicData().getDynamicSupplies());
        Collections.sort(supplies, (DynamicSupply s1, DynamicSupply s2) -> {
            return Long.compare(s1.getProduct().getId(), s2.getProduct().getId());
        });
        return supplies;
    }

    /**
     *
     * @return
     */
    public Collection<DynamicDemand> getDemands() {
        List<DynamicDemand> demands = new ArrayList<>(game.getEconomy().getEconomicData().getDynamicDemands());
        Collections.sort(demands, (DynamicDemand d1, DynamicDemand d2) -> {
            return Long.compare(d1.getProduct().getId(), d2.getProduct().getId());
        });
        return demands;
    }

    /**
     *
     */
    public void updateForm() {

    }

    /**
     *
     */
    public void updatePage() {

    }

    /**
     *
     * @return
     */
    public Company getCompany() {
        return cooperator.getCompany();
    }

    /**
     *
     * @return
     */
    public Warehouse getWarehouse() {
        return cooperator.getCompany().getWarehouse();
    }

    /**
     *
     * @return
     */
    public Balance getBalance() {
        return cooperator.getCompany().getBalance();
    }

    /**
     *
     * @return
     */
    public Factory getFactory() {
        return cooperator.getCompany().getFactory();
    }

    /**
     *
     * @return
     */
    public Sector getSector() {
        return cooperator.getCompany().getSector();
    }

    /**
     *
     * @return
     */
    public Production getProduction() {
        return cooperator.getCompany().getProduction();
    }

    /**
     *
     * @return
     */
    public Collection<Company> getCompetitors() {
        List<Company> competitors = new ArrayList<>();
        Set<Company> companies = game.getEconomy().getCompanies();
        companies.stream().filter((Company company) -> (!company.equals(cooperator.getCompany()))).forEach((Company company) -> {
            competitors.add(company);
        });
        Collections.sort(competitors);
        return competitors;
    }

}
