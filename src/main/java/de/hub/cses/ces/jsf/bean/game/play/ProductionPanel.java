package de.hub.cses.ces.jsf.bean.game.play;

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
import de.hub.cses.ces.entity.production.Production;
import de.hub.cses.ces.entity.production.ProductionPlan;
import de.hub.cses.ces.jsf.bean.game.PlayBean;
import de.hub.cses.ces.jsf.config.GamePlayComponent;
import de.hub.cses.ces.service.persistence.production.ProductionFacade;
import de.hub.cses.ces.service.persistence.production.ProductionPlanFacade;
import de.hub.cses.ces.util.ComponentUpdateUtil;
import java.io.Serializable;
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
@Named("ProductionPanel")
@ViewScoped
public class ProductionPanel implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @Inject
    private PlayBean gamePlay;

    @Inject
    private ComponentUpdateUtil componentUpdateUtil;

    @EJB
    private ProductionFacade productionFacade;

    @EJB
    private ProductionPlanFacade productionPlanFacade;

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
    public Production getProduction() {
        return gamePlay.getCooperator().getCompany().getProduction();
    }

    /**
     *
     * @param event
     * @throws Exception
     */
    public void edit(org.primefaces.event.RowEditEvent event) throws Exception {
        ProductionPlan productionPlan = (ProductionPlan) event.getObject();
        try {
            logger.log(Level.INFO, "edit production plan {0}", (productionPlan != null) ? productionPlan.getId() : null);
            logger.log(Level.INFO, "workforce {0}", (productionPlan != null) ? productionPlan.getWorkforce() : null);
            productionPlanFacade.edit(productionPlan);
        } catch (Exception ex) {
            Exception ne = (Exception) ex.getCause();
            if ("org.eclipse.persistence.exceptions.OptimisticLockException".equals(ne.getClass().getName())
                    || "javax.persistence.OptimisticLockException".equals(ne.getClass().getName())) {
                throw new javax.persistence.OptimisticLockException("fehler...");
            }
        } finally {
            componentUpdateUtil.companyUpdate(gamePlay.getCooperator().getCompany().getId(), GamePlayComponent.PRODUCTION);
        }

    }

    /**
     *
     * @param event
     */
    public void cancel(org.primefaces.event.RowEditEvent event) {
        //gamePlay.updateData();
    }
}
