package de.hub.cses.ces.service.simulation.calculator;

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
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.Factory;
import de.hub.cses.ces.entity.economy.DynamicDemand;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.market.BaseDemand;
import de.hub.cses.ces.entity.product.Product;
import de.hub.cses.ces.entity.production.ProductionPlan;
import de.hub.cses.ces.util.DoubleUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@RequestScoped
public class DynamicDemandCalculator extends EconomyCalculator {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @Inject
    private DoubleUtil doubleUtil;

    /**
     *
     */
    public DynamicDemandCalculator() {

    }

    /**
     *
     * @param economy
     * @param current
     * @throws NullPointerException
     */
    @Override
    public void calculate(Economy economy, Date current) throws NullPointerException {
        Set<BaseDemand> baseDemands = economy.getMarket().getBaseDemands();
        if (baseDemands == null || baseDemands.isEmpty()) {
            logger.log(Level.WARNING, "no base supplies found");
            // @TODO check prerequisites
            return;
        }
        Collection<DynamicDemand> _dynamicDemands = economy.getEconomicData().getDynamicDemands();
        if (_dynamicDemands == null || _dynamicDemands.isEmpty()) {
            init(economy, current);
            return;
        }
        double demandFactor = calculateDemandFactor(economy);
        Map<Product, Double> satisfactionFactors = calculateSatisfactionFactors(_dynamicDemands);
        List<DynamicDemand> dynamicDemands = calculate(baseDemands, _dynamicDemands, current, demandFactor, satisfactionFactors);
        economy.getEconomicData().getHistoricalEconomicData().getDynamicDemands().addAll(dynamicDemands);
    }

    /**
     *
     * @param economy
     * @param current
     * @throws NullPointerException
     */
    @Override
    public void init(Economy economy, Date current) throws NullPointerException {
        double demandFactor = calculateDemandFactor(economy);
        List<DynamicDemand> demands = calculate(economy.getMarket().getBaseDemands(), null, current, demandFactor);
        economy.getEconomicData().setDynamicDemands(demands);
    }

    private List<DynamicDemand> calculate(Set<BaseDemand> baseDemands, Collection<DynamicDemand> _dynamicDemands, Date current, double demandFactor) {
        return calculate(baseDemands, _dynamicDemands, current, demandFactor, null);
    }

    private List<DynamicDemand> calculate(Set<BaseDemand> baseDemands, Collection<DynamicDemand> _dynamicDemands, Date current, double demandFactor, Map<Product, Double> satisfactionFactors) {
        List<DynamicDemand> dynamicDemands = new ArrayList<>();
        baseDemands.stream().map((BaseDemand baseDemand) -> {
            double randomDemandFactor = doubleUtil.randDouble(0.95, 1.05);
            double randomPriceFactor = doubleUtil.randDouble(0.95, 1.05);

            Product product = baseDemand.getProduct();
            DynamicDemand _dynamicDemand = findDynamicDemand(product, _dynamicDemands);
            double satisfactionFactor = 1d;
            if (satisfactionFactors != null && satisfactionFactors.containsKey(product)) {
                satisfactionFactor = satisfactionFactors.get(product);
            }

            int quantity = (int) ((demandFactor * randomDemandFactor * satisfactionFactor) * baseDemand.getQuantity());

            double price = Math.min(Math.max(getPrice(product) * 0.25, getPrice(product) * randomPriceFactor * (1d / satisfactionFactor)), 4 * getPrice(product));

            int _quantity = (_dynamicDemand != null) ? _dynamicDemand.getQuantity() : 0;
            int _availableQuantity = (_dynamicDemand != null) ? _dynamicDemand.getAvailableQuantity() : 0;

            int newQuantity = _availableQuantity + quantity;

            Date calculated = (_dynamicDemand != null) ? _dynamicDemand.getCalculated() : current;

            if (_dynamicDemand != null) {
                _dynamicDemand.setQuantity(newQuantity);
                _dynamicDemand.setAvailableQuantity(newQuantity);
                _dynamicDemand.setPricePerUnit(price);
                _dynamicDemand.setCalculated(current);
            }

            DynamicDemand dynamicDemand = createDynamicDemand(product, _quantity, price, calculated);
            return dynamicDemand;
        }).forEach((dynamicDemand) -> {
            dynamicDemands.add(dynamicDemand);
        });
        return dynamicDemands;
    }

    private DynamicDemand createDynamicDemand(Product product, int quantity, double price, Date current) {
        DynamicDemand demand = new DynamicDemand();
        demand.setProduct(product);
        demand.setQuantity(quantity);
        demand.setAvailableQuantity(quantity);
        demand.setPricePerUnit(doubleUtil.round(price, 2));
        demand.setCalculated(current);
        return demand;
    }

    private double calculateDemandFactor(Economy economy) throws NullPointerException {
        double demandFactor = 0d;
        for (Company company : economy.getCompanies()) {
            Factory factory = company.getFactory();
            int overallWorkForce = 0;
            overallWorkForce = company.getProduction().getProductionPlans().stream().map((ProductionPlan productionPlan) -> productionPlan.getWorkforce()).reduce(overallWorkForce, Integer::sum);
            demandFactor += ((double) overallWorkForce / (double) factory.getMaxWorkforce());
        }
        demandFactor = demandFactor / economy.getCompanies().size();
        demandFactor = Math.max(1d, demandFactor);
        return demandFactor;
    }

    private Map<Product, Double> calculateSatisfactionFactors(Collection<DynamicDemand> dynamicDemands) {
        Map<Product, Double> satisfactionFactors = new HashMap<>();
        if (dynamicDemands != null && !dynamicDemands.isEmpty()) {
            for (DynamicDemand dynamicSupply : dynamicDemands) {
                int quantity = dynamicSupply.getQuantity();
                int availableQuantity = dynamicSupply.getAvailableQuantity();
                double satisfactionFactor = 1d;
                if (quantity > 0) {
                    double ratio = (quantity - availableQuantity) / quantity;
                    satisfactionFactor = Math.log(Math.sqrt(Math.pow((1 + ratio), 3) + 1));
                }
                satisfactionFactors.put(dynamicSupply.getProduct(), satisfactionFactor);
            }
        }
        return satisfactionFactors;
    }

    private DynamicDemand findDynamicDemand(Product product, Collection<DynamicDemand> dynamicDemands) {
        if (product != null && dynamicDemands != null && !dynamicDemands.isEmpty()) {
            for (DynamicDemand dynamicSupply : dynamicDemands) {
                if (product.equals(dynamicSupply.getProduct())) {
                    return dynamicSupply;
                }
            }
        }
        return null;
    }

}
