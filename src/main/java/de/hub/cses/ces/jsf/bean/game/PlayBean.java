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
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import de.hub.cses.ces.entity.economy.DynamicDemand;
import de.hub.cses.ces.entity.economy.DynamicSupply;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.production.Production;
import de.hub.cses.ces.entity.text.I18nText;
import de.hub.cses.ces.entity.text.SupportedLanguage;
import de.hub.cses.ces.event.Notification;
import de.hub.cses.ces.event.NotificationType;
import de.hub.cses.ces.jsf.bean.util.RedirectionUtil;
import de.hub.cses.ces.service.observer.NotificationService;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.util.I18nTextUtil;
import de.hub.cses.ces.util.NotificationUtil;
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
import java.util.Random;
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
    
    private String chatMessage;

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
    

    // Ole
    public void sendChat() {
        NotificationType type = null;
        
        String fullMessage = this.cooperator.getUser().getClientname() + ": " + getChatMessage(); 

        Map<SupportedLanguage, String> msg = new HashMap<SupportedLanguage, String>();
        msg.put(SupportedLanguage.DEUTSCH, fullMessage);
        
        long companyId = getCompany().getId();
       
//        NotificationUtil nu = new NotificationUtil();
//        nu.notifyCompany(companyId, msg, NotificationType.INFO);
//        nu.notifyGame(getGameId() , msg, NotificationType.INFO);
//        nu.notifyClients(msg, NotificationType.INFO);
        
        Notification notification = new Notification(getCompany().getId(), NotificationType.INFO, new I18nTextUtil().create(msg));
        notificationObserver.addCompanyNotification(notification);
        
        System.out.println("Notificationlist: " + notificationObserver.getAll(gameId, cooperator.getCompany().getId(), cooperator.getId(), cooperator.getUser().getId()).get(0));
        
//        Collection<Notification> c = getNotifications();
//        System.out.println("Notifications: " + c.toString());
    }
    
    
        /**
     *
     * @return
     */
    // Ole
    public String getChatMessage() {
        return chatMessage;
    }
    
        /**
     *
     * @param chatMessage
     */
    // Ole
    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    /**
     *
     * @return
     */
    public Collection<Notification> getNotifications() {
//        List<Notification> notifications = notificationObserver.getAll(gameId, cooperator.getCompany().getId(), cooperator.getId(), cooperator.getUser().getId());
        List<Notification> notifications = notificationObserver.getCompanyNotifications(cooperator.getCompany().getId());

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
