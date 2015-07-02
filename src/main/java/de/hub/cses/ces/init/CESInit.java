package de.hub.cses.ces.init;

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
import de.hub.cses.ces.entity.company.cooperator.role.ManufacturerRole;
import de.hub.cses.ces.entity.company.cooperator.role.MarketerRole;
import de.hub.cses.ces.entity.company.cooperator.role.PurchaserRole;
import de.hub.cses.ces.entity.company.cooperator.role.Role;
import de.hub.cses.ces.entity.company.cooperator.role.SellerRole;
import de.hub.cses.ces.entity.market.Market;
import de.hub.cses.ces.init.market.HomeElectronicsMarket;
import de.hub.cses.ces.service.persistence.company.cooperator.RoleFacade;
import de.hub.cses.ces.service.persistence.economy.market.MarketFacade;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * initializes an example market defined in HomeElectronicsMarket that can be
 * used to create new simulation games
 *
 * if you want to reset these data, please comment out the reset method in the
 * init method
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Singleton
@Startup
public class CESInit implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private RoleFacade roleFacade;

    @EJB
    private MarketFacade marketFacade;

    /**
     *
     */
    @PostConstruct
    public void init() {
        // reset();
        Market homeElectronicsMarket = marketFacade.find(1L);
        if (homeElectronicsMarket == null) {
            createMarkets();
        }
        List<Role> roles = roleFacade.findAll();
        if (roles == null || roles.isEmpty()) {
            createRoles();
        }
    }

    private void reset() {
        resetMarkets();
        resetRoles();
    }

    private void resetMarkets() {
        removeMarkets();
        createMarkets();
    }

    private void removeMarkets() {
        logger.log(Level.INFO, "remove markets");
        List<Market> markets = marketFacade.findAll();
        if (markets != null && !markets.isEmpty()) {
            markets.stream().forEach((Market market) -> {
                marketFacade.remove(market);
            });
            marketFacade.flush();
        }
    }

    private void createMarkets() {
        logger.log(Level.INFO, "create markets");
        Market homeElectronicsMarket = HomeElectronicsMarket.create();
        marketFacade.create(homeElectronicsMarket);
    }

    private void resetRoles() {
        removeRoles();
        createRoles();
    }

    private void removeRoles() {
        logger.log(Level.INFO, "remove roles");
        List<Role> roles = roleFacade.findAll();
        if (roles != null && !roles.isEmpty()) {
            roles.stream().forEach((Role role) -> {
                roleFacade.remove(role);
            });
            roleFacade.flush();
        }
    }

    private void createRoles() {
        logger.log(Level.INFO, "create roles");
        Role role1 = new PurchaserRole(1L);
        roleFacade.create(role1);
        Role role2 = new ManufacturerRole(2L);
        roleFacade.create(role2);
        Role role3 = new SellerRole(3L);
        roleFacade.create(role3);
        Role role4 = new MarketerRole(4L);
        roleFacade.create(role4);
    }

}
