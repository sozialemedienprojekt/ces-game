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
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.entity.company.cooperator.role.Role;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.service.persistence.company.CompanyFacade;
import de.hub.cses.ces.service.persistence.company.cooperator.CooperatorFacade;
import de.hub.cses.ces.service.persistence.company.cooperator.RoleFacade;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.util.qualifier.Identify;
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

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("JoinBean")
@ViewScoped
public class JoinBean implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private GameFacade gameFacade;

    @EJB
    private RoleFacade roleFacade;

    @EJB
    private CooperatorFacade cooperatorFacade;

    @EJB
    private CompanyFacade companyFacade;

    @Inject
    @Identify
    private User user;

    private Collection<Role> roles;

    private Long gameId;

    private Game game;
    private Company company;
    private Cooperator cooperator;

    /**
     *
     */
    public JoinBean() {

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
    public Long getGameId() {
        return gameId;
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
    public Collection<Game> getGames() {
        return gameFacade.findAllByStatus(GameStatus.INITIALIZED);
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
     * @return
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
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
     */
    public void selectGame() {
        this.cooperator = null;
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

}
