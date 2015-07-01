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
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.entity.company.cooperator.role.Role;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.service.persistence.company.CompanyFacade;
import de.hub.cses.ces.service.persistence.company.cooperator.CooperatorFacade;
import de.hub.cses.ces.service.persistence.company.cooperator.RoleFacade;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.service.simulation.SimulationInitializer;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("SetupBean")
@ViewScoped
public class SetupBean implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private GameFacade gameFacade;

    @EJB
    private RoleFacade roleFacade;

    @EJB
    private CompanyFacade companyFacade;

    @EJB
    private CooperatorFacade cooperatorFacade;

    @EJB
    private SimulationInitializer simulationStarter;

    //@Inject
    //@Identify
    private User user;

    private Game game;

    private Collection<Role> roles;
    private Company company;
    private Cooperator cooperator;
    private Sector sector;
    private Factory factory;
    private FinalProduct finalProduct;

    private String password;
    private String companyIdentifier;

    private boolean unlocked = false;

    /**
     *
     */
    public SetupBean() {

    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        updateRoles();
    }

    private void updateRoles() {
        roles = roleFacade.findAll();
    }

    /**
     *
     * @return
     */
    public Collection<Game> getGames() {
        return gameFacade.findAllByStatus(GameStatus.INITIALIZED);
    }

    /**
     *
     * @param gameId
     */
    public void setGameId(Long gameId) {
        if (gameId != null) {
            game = gameFacade.find(gameId);
            this.unlocked = (game == null || game.getPassword() == null);
        }
    }

    /**
     *
     * @return
     */
    public Long getGameId() {
        if (game == null) {
            return null;
        }
        return game.getId();
    }

    /**
     *
     * @param game
     */
    public void setGame(Game game) {
        logger.log(Level.INFO, "set game");
        this.game = game;
    }

    /**
     *
     * @return
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @return
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * @param company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     *
     * @return
     */
    public Collection<Role> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles
     */
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
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
     * @param cooperator
     */
    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }

    /**
     *
     * @param sector
     */
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    /**
     *
     * @return
     */
    public Sector getSector() {
        return sector;
    }

    /**
     *
     * @return
     */
    public Factory getFactory() {
        return factory;
    }

    /**
     *
     * @param factory
     */
    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public FinalProduct getFinalProduct() {
        return finalProduct;
    }

    /**
     *
     * @param finalProduct
     */
    public void setFinalProduct(FinalProduct finalProduct) {
        this.finalProduct = finalProduct;
    }

    /**
     *
     */
    public void selectGame() {
        this.cooperator = null;
        this.sector = null;
        this.factory = null;
        this.unlocked = (game == null || game.getPassword() == null);
    }

    /**
     *
     */
    public void selectCompany() {
        logger.log(Level.INFO, "select company {0}.", company);
        if (user != null) {
            this.cooperator = cooperatorFacade.findByUserAndCompany(user, company);
        }
    }

    /**
     *
     */
    public void resetGame() {
        this.password = null;
    }

    /**
     *
     */
    public void removeCompany() {
        game.getEconomy().getCompanies().remove(company);
        gameFacade.edit(game);
        companyFacade.remove(company);
        company = null;
    }

    /**
     *
     */
    public void joinCompany() {
        if (user != null) {
            this.cooperator = new Cooperator();
            cooperator.setUser((User) user);
            cooperator.setCompany(company);
            cooperatorFacade.create(cooperator);
            Set<Cooperator> cooperators = company.getCooperators();
            if (cooperators == null) {
                cooperators = new HashSet<>();
                company.setCooperators(cooperators);
            }
            cooperators.add(cooperator);
            companyFacade.edit(company);
        }
    }

    /**
     *
     */
    public void leaveCompany() {
        if (cooperator != null) {
            company.getCooperators().remove(cooperator);
            companyFacade.edit(company);
            cooperatorFacade.remove(cooperator);
            cooperator = null;
        }
    }

    /**
     *
     */
    public void updateCooperatorRoles() {
        if (cooperator != null) {
            logger.log(Level.INFO, "cooperator version {0}", cooperator.getVersion());
            cooperatorFacade.edit(cooperator);
            cooperator = cooperatorFacade.find(cooperator.getId());
            logger.log(Level.INFO, "cooperator version {0}", cooperator.getVersion());

        }
    }

    /**
     *
     * @return
     */
    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    /**
     *
     * @param companyIdentifier
     */
    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    /**
     *
     */
    public void createCompany() {
        company = companyFacade.createCompany(companyIdentifier, game.getEconomy(), sector, factory);
        try {
            Set<Company> companies = game.getEconomy().getCompanies();
            if (companies == null) {
                companies = new HashSet<>();
                game.getEconomy().setCompanies(companies);
            }
            companies.add(company);
        } catch (NullPointerException npe) {

        }
        game = gameFacade.edit(game);
        this.sector = null;
        this.factory = null;
        this.companyIdentifier = null;
    }

    /**
     *
     */
    public void setMarketSector() {
        this.factory = null;
    }

    /**
     *
     * @return
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     *
     */
    public void unlock() {
        if (this.password == null) {
            return;
        }
        String secret = DigestUtils.sha512Hex(password);
        if (secret.equals(game.getPassword())) {
            this.unlocked = true;
        }
    }

    public void cancel() {
        this.companyIdentifier = null;
        this.cooperator = null;
        this.sector = null;
        this.factory = null;
    }

}
