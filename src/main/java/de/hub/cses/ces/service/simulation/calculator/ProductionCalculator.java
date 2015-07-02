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
import de.hub.cses.ces.entity.company.warehouse.Stock;
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.Part;
import de.hub.cses.ces.entity.product.PartsList;
import de.hub.cses.ces.entity.product.Product;
import de.hub.cses.ces.entity.production.Production;
import de.hub.cses.ces.entity.production.ProductionPlan;
import de.hub.cses.ces.service.simulation.accounting.StockTransactionAccounting;
import de.hub.cses.ces.util.DoubleUtil;
import de.hub.cses.ces.util.StocksUtil;
import java.math.RoundingMode;
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
public class ProductionCalculator extends CompanyCalculator {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @Inject
    private StockTransactionAccounting stockTransactionAccounting;

    @Inject
    private StocksUtil stockFinder;

    @Inject
    private DoubleUtil doubleUtil;

    /**
     *
     */
    public ProductionCalculator() {

    }

    /**
     *
     * @param company
     * @param current
     * @throws NullPointerException
     */
    @Override
    public void calculate(Company company, Date current) throws NullPointerException {
        Map<Product, Double> producibleQuantities = getProducibleQuantities(company);
        List<ProductionPlan> productionPlans = company.getProduction().getProductionPlans();
        List<Stock> stocks = company.getWarehouse().getStocks();
        for (ProductionPlan productionPlan : productionPlans) {
            FinalProduct finalProduct = productionPlan.getFinalProduct();
            if (producibleQuantities.containsKey(finalProduct)) {
                double producibleQuantity = producibleQuantities.get(finalProduct);
                if (producibleQuantity > 0.0d) {
                    Set<Part> parts = finalProduct.getPartsList().getParts();
                    parts.stream().forEach((Part part) -> {
                        Product product = part.getIntermediateProduct();
                        Stock stock = stockFinder.find(stocks, product);
                        double necessaryQuantity = Math.min(stock.getQuantity(), producibleQuantity * part.getQuantity());
                        stockTransactionAccounting.account(stock, -necessaryQuantity, current);
                    });
                    Stock stock = stockFinder.find(stocks, finalProduct);
                    stockTransactionAccounting.account(stock, producibleQuantity, current);
                }
            }
        }
    }

    private Map<Product, Double> getProducibleQuantities(Company company) {
        Map<Product, Double> producibleQuantities;
        try {
            double workingHoursPerDay = company.getEconomy().getMarket().getWorkingHoursPerDay();
            Warehouse warehouse = company.getWarehouse();
            List<Stock> stocks = warehouse.getStocks();
            Production production = company.getProduction();
            List<ProductionPlan> productionPlans = production.getProductionPlans();
            Map<Product, Double> necessaryQuantities = getNecessaryQuantities(productionPlans, workingHoursPerDay);
            Map<Product, Double> quantityCoefficients = getQuantityCoefficients(stocks, necessaryQuantities);
            producibleQuantities = getProducibleQuantities(productionPlans, quantityCoefficients, workingHoursPerDay);
        } catch (NullPointerException npe) {
            logger.log(Level.WARNING, null, npe);
            producibleQuantities = null;
        }
        return producibleQuantities;
    }

    private Map<Product, Double> getNecessaryQuantities(List<ProductionPlan> productionPlans, double workingHoursPerDay) throws NullPointerException {
        Map<Product, Double> necessaryQuantities = new HashMap<>();
        for (ProductionPlan productionPlan : productionPlans) {
            int workforce = productionPlan.getWorkforce();
            if (workforce > 0) {
                double availableWorkingTime = workingHoursPerDay * (double) workforce;
                FinalProduct finalProduct = productionPlan.getFinalProduct();
                double requiredWorkingTime = finalProduct.getRequiredWorkingTime();
                double producibleProductsByTime = (availableWorkingTime / requiredWorkingTime);
                PartsList partsList = finalProduct.getPartsList();
                Set<Part> parts = partsList.getParts();
                parts.stream().forEach((Part part) -> {
                    Product product = part.getIntermediateProduct();
                    double quantity = part.getQuantity();
                    double necessaryQuantity = (quantity * producibleProductsByTime);
                    if (necessaryQuantities.containsKey(product)) {
                        necessaryQuantity += necessaryQuantities.get(product);
                    }
                    necessaryQuantities.put(product, necessaryQuantity);
                });
            }
        }
        return necessaryQuantities;
    }

    private Map<Product, Double> getQuantityCoefficients(List<Stock> stocks, Map<Product, Double> necessaryQuantities) throws NullPointerException {
        Map<Product, Double> quantityCoefficients = new HashMap<>();
        for (Stock stock : stocks) {
            Product product = stock.getProduct();
            if (necessaryQuantities.containsKey(product)) {
                double necessaryQuantity = necessaryQuantities.get(product);
                double availableQuantity = stock.getQuantity();
                double quantityCoefficient = Math.max(0d, Math.min(1d, doubleUtil.round((availableQuantity / necessaryQuantity), 3, RoundingMode.FLOOR)));
                quantityCoefficients.put(product, quantityCoefficient);
            }
        }
        return quantityCoefficients;
    }

    private Map<Product, Double> getProducibleQuantities(List<ProductionPlan> productionPlans, Map<Product, Double> quantityCoefficients, double workingHoursPerDay) throws NullPointerException {
        Map<Product, Double> producibleQuantities = new HashMap<>();
        for (ProductionPlan productionPlan : productionPlans) {
            int workforce = productionPlan.getWorkforce();
            if (workforce > 0) {
                Double producibleQuantity = null;
                FinalProduct finalProduct = productionPlan.getFinalProduct();
                double availableWorkingTime = workingHoursPerDay * (double) workforce;
                double requiredWorkingTime = finalProduct.getRequiredWorkingTime();
                double producibleProductsByTime = (availableWorkingTime / requiredWorkingTime);
                PartsList partsList = finalProduct.getPartsList();
                Set<Part> parts = partsList.getParts();

                for (Part part : parts) {
                    Product product = part.getIntermediateProduct();
                    if (quantityCoefficients.containsKey(product)) {
                        double quantity = part.getQuantity();
                        double necessaryQuantityByTime = quantity * producibleProductsByTime;
                        double quantityCoefficient = quantityCoefficients.get(product);
                        double _producibleQuantity = doubleUtil.round(((necessaryQuantityByTime * quantityCoefficient) / quantity), 2);
                        if ((producibleQuantity == null) || (Double.compare(_producibleQuantity, producibleQuantity) < 0)) {
                            producibleQuantity = _producibleQuantity;
                        }
                    }
                }
                if (producibleQuantity != null) {
                    producibleQuantities.put(finalProduct, producibleQuantity);
                } else {
                    producibleQuantities.put(finalProduct, 0d);
                }
            }
        }
        return producibleQuantities;
    }

}
