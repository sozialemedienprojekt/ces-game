package de.hub.cses.ces.jsf.component;

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
import de.hub.cses.ces.entity.company.Factory;
import de.hub.cses.ces.entity.production.Production;
import de.hub.cses.ces.entity.production.ProductionPlan;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.model.DataModel;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.SlideEndEvent;
import org.primefaces.model.CollectionDataModel;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@FacesComponent("ProductionPanelComponent")
public class ProductionPanelComponent extends UINamingContainer {

    private static final Logger logger = Logger.getLogger(ProductionPanelComponent.class.getName());

    private int workforce = -1;

    /**
     *
     * @return
     */
    public int getNumberOfBusyWorkforce() {
        Collection<ProductionPlan> productionPlans = (List<ProductionPlan>) getAttributes().get("productionPlans");
        int numberOfBusyWorkforce = 0;
        if (productionPlans != null && !productionPlans.isEmpty()) {
            for (ProductionPlan productionPlan : productionPlans) {
                numberOfBusyWorkforce += productionPlan.getWorkforce();
            }
        }
        return numberOfBusyWorkforce;
    }

    private Factory getFactory() {
        return (Factory) getAttributes().get("factory");
    }

    private Production getProduction() {
        return (Production) getAttributes().get("production");
    }

    /**
     *
     * @return
     */
    public List<ProductionPlan> getProductionPlans() {
        List<ProductionPlan> productionPlans = new ArrayList<>();
        Production production = getProduction();
        if (production != null) {
            productionPlans.addAll(production.getProductionPlans());
            Collections.sort(productionPlans);
        }
        return productionPlans;
    }

    /**
     *
     * @return
     */
    public DataModel<ProductionPlan> getModel() {
        Production production = getProduction();
        DataModel<ProductionPlan> model = new CollectionDataModel<>(production.getProductionPlans());
        return model;
    }

    /**
     *
     * @return
     */
    public int getNumberOfIdleCoreWorkforce() {

        int coreWorkforce = getFactory().getCoreWorkforce();
        int busyWorkforce = getNumberOfBusyWorkforce();
        return (coreWorkforce > busyWorkforce) ? (coreWorkforce - busyWorkforce) : 0;
    }

    /**
     *
     * @param productionPlan
     * @return
     */
    public double getProducibleQuantity(ProductionPlan productionPlan) {
        int _workforce = workforce;
        if (_workforce == -1) {
            _workforce = productionPlan.getWorkforce();
        }
        double producibleQuantity = (productionPlan.getProduction().getCompany().getEconomy().getMarket().getWorkingHoursPerDay() * _workforce) / productionPlan.getFinalProduct().getRequiredWorkingTime();
        workforce = -1;
        return producibleQuantity;

    }

    /**
     *
     * @return
     */
    public int getMaxWorkforce() {
        int maxWorkforce = getFactory().getMaxWorkforce();
        int busyWorkforce = getNumberOfBusyWorkforce();
        int max = maxWorkforce - busyWorkforce;
        return max;
    }

    /**
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        ProductionPlan productionPlan = (ProductionPlan) event.getObject();
        getAttributes().put("selectedProductionPlan", productionPlan);
    }

    /**
     *
     * @param event
     */
    public void onEditInit(RowEditEvent event) {
        ProductionPlan productionPlan = (ProductionPlan) event.getObject();
        getAttributes().put("selectedProductionPlan", productionPlan);
    }

    /**
     *
     * @param event
     */
    public void onEdit(RowEditEvent event) {
        ProductionPlan productionPlan = (ProductionPlan) event.getObject();
        getAttributes().put("selectedProductionPlan", productionPlan);
    }

    /**
     *
     * @param event
     */
    public void onSlideEnd(SlideEndEvent event) {
        this.workforce = event.getValue();
    }

}
