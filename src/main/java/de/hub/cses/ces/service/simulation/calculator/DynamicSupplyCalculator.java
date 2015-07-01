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
import de.hub.cses.ces.entity.economy.DynamicSupply;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.market.BaseSupply;
import de.hub.cses.ces.entity.product.Product;
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
public class DynamicSupplyCalculator extends EconomyCalculator {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @Inject
    private DoubleUtil doubleUtil;

    /**
     *
     */
    public DynamicSupplyCalculator() {

    }

    /**
     *
     * @param economy
     * @param current
     * @throws NullPointerException
     */
    @Override
    public void calculate(Economy economy, Date current) throws NullPointerException {
        Set<BaseSupply> baseSupplies = economy.getMarket().getBaseSupplies();
        if (baseSupplies == null || baseSupplies.isEmpty()) {
            logger.log(Level.WARNING, "no base supplies found");
            // @TODO check prerequisites
            return;
        }
        Collection<DynamicSupply> _dynamicSupplies = economy.getEconomicData().getDynamicSupplies();
        if (_dynamicSupplies == null || _dynamicSupplies.isEmpty()) {
            init(economy, current);
            return;
        }
        double supplyFactor = calculateSupplyFactor(economy);
        Map<Product, Double> consumptionFactors = calculateConsumptionFactors(economy.getEconomicData().getDynamicSupplies());
        List<DynamicSupply> dynamicSupplies = calculate(baseSupplies, _dynamicSupplies, current, supplyFactor, consumptionFactors);
        economy.getEconomicData().getHistoricalEconomicData().getDynamicSupplies().addAll(dynamicSupplies);
    }

    /**
     *
     * @param economy
     * @param current
     * @throws NullPointerException
     */
    @Override
    public void init(Economy economy, Date current) throws NullPointerException {
        double supplyFactor = calculateSupplyFactor(economy);
        List<DynamicSupply> supplies = calculate(economy.getMarket().getBaseSupplies(), null, current, supplyFactor);
        economy.getEconomicData().setDynamicSupplies(supplies);
    }

    private List<DynamicSupply> calculate(Set<BaseSupply> baseSupplies, Collection<DynamicSupply> _dynamicSupplies, Date current, double supplyFactor) {
        return calculate(baseSupplies, _dynamicSupplies, current, supplyFactor, null);
    }

    private List<DynamicSupply> calculate(Set<BaseSupply> baseSupplies, Collection<DynamicSupply> _dynamicSupplies, Date current, double supplyFactor, Map<Product, Double> consumptionFactors) {

        List<DynamicSupply> dynamicSupplies = new ArrayList<>();
        baseSupplies.stream().map((BaseSupply baseSupply) -> {

            double randomSupplyFactor = doubleUtil.randDouble(0.95, 1.05);
            double randomPriceFactor = doubleUtil.randDouble(0.95, 1.05);

            Product product = baseSupply.getProduct();
            DynamicSupply _dynamicSupply = findDynamicSupply(product, _dynamicSupplies);
            double consumptionFactor = 1d;
            if (consumptionFactors != null && consumptionFactors.containsKey(product)) {
                consumptionFactor = consumptionFactors.get(product);
            }
            int quantity = (int) ((supplyFactor * randomSupplyFactor * consumptionFactor) * baseSupply.getQuantity());

            double _price = (_dynamicSupply != null) ? _dynamicSupply.getPricePerUnit() : getPrice(product);
            double price = Math.min(Math.max(getPrice(product) * 0.25, _price * randomPriceFactor * consumptionFactor), getPrice(product) * 4d);

            int _quantity = (_dynamicSupply != null) ? _dynamicSupply.getQuantity() : 0;
            int _availableQuantity = (_dynamicSupply != null) ? _dynamicSupply.getAvailableQuantity() : 0;

            int newQuantity = _availableQuantity + quantity;

            Date calculated = (_dynamicSupply != null) ? _dynamicSupply.getCalculated() : current;

            if (_dynamicSupply != null) {
                _dynamicSupply.setQuantity(newQuantity);
                _dynamicSupply.setAvailableQuantity(newQuantity);
                _dynamicSupply.setPricePerUnit(price);
                _dynamicSupply.setCalculated(current);
            }

            DynamicSupply dynamicSupply = createDynamicSupply(product, _quantity, _availableQuantity, _price, calculated);
            return dynamicSupply;
        }).forEach((dynamicSupply) -> {
            dynamicSupplies.add(dynamicSupply);
        });
        return dynamicSupplies;
    }

    private Map<Product, Double> calculateConsumptionFactors(Collection<DynamicSupply> dynamicSupplies) {
        Map<Product, Double> consumptionFactors = new HashMap<>();
        if (dynamicSupplies != null && !dynamicSupplies.isEmpty()) {
            for (DynamicSupply dynamicSupply : dynamicSupplies) {
                int quantity = dynamicSupply.getQuantity();
                int availableQuantity = dynamicSupply.getAvailableQuantity();
                double consumptionFactor = 1d;
                if (quantity > 0) {
                    // (ln(sqrt(1 + ((quantity - availableQuantity) / quantity)^3 + 1)))^2
                    double ratio = (quantity - availableQuantity) / quantity;
                    consumptionFactor = Math.pow(Math.log(Math.sqrt(Math.pow(1 + ratio, 3) + 1)), 2);
                }
                consumptionFactors.put(dynamicSupply.getProduct(), consumptionFactor);
            }
        }
        return consumptionFactors;
    }

    private DynamicSupply createDynamicSupply(Product product, int quantity, int availableQuantity, double price, Date current) {
        DynamicSupply supply = new DynamicSupply();
        supply.setProduct(product);
        supply.setQuantity(quantity);
        supply.setAvailableQuantity(availableQuantity);
        supply.setPricePerUnit(price);
        supply.setCalculated(current);
        return supply;
    }

    private double calculateSupplyFactor(Economy economy) {
        double supplyFactor = 0d;
        supplyFactor = economy.getCompanies().stream().map((Company company) -> company.getFactory().getSupplyFactor()).reduce(supplyFactor, (accumulator, _item) -> accumulator + _item);
        supplyFactor = Math.max(supplyFactor, 0.1d);
        supplyFactor = Math.log(Math.sqrt(Math.exp(supplyFactor)));
        supplyFactor = Math.pow(supplyFactor, 2d);
        supplyFactor = Math.max(1d, supplyFactor);
        return supplyFactor;
    }

    private DynamicSupply findDynamicSupply(Product product, Collection<DynamicSupply> _dynamicSupplies) {
        if (product != null && _dynamicSupplies != null && !_dynamicSupplies.isEmpty()) {
            for (DynamicSupply dynamicSupply : _dynamicSupplies) {
                if (product.equals(dynamicSupply.getProduct())) {
                    return dynamicSupply;
                }
            }
        }
        return null;
    }
}
