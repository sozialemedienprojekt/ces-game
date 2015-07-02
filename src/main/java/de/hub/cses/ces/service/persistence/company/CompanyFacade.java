package de.hub.cses.ces.service.persistence.company;

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
import de.hub.cses.ces.entity.company.accounting.Balance;
import de.hub.cses.ces.entity.company.warehouse.Stock;
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.IntermediateProduct;
import de.hub.cses.ces.entity.product.Part;
import de.hub.cses.ces.entity.production.Production;
import de.hub.cses.ces.entity.production.ProductionPlan;
import de.hub.cses.ces.service.persistence.AbstractFacade;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class CompanyFacade extends AbstractFacade<Company> {

    @PersistenceContext(unitName = "de.hub.cses.ces.PersistenceUnit")
    private EntityManager em;

    /**
     *
     */
    public CompanyFacade() {
        super(Company.class);
    }

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @param identifier
     * @param economy
     * @param sector
     * @param factory
     * @return
     */
    public Company createCompany(String identifier, Economy economy, Sector sector, Factory factory) {
        Company company = new Company();
        company.setIdentifier(identifier);
        company.setEconomy(economy);
        company.setSector(sector);
        company.setFactory(factory);
        company.setCooperators(new HashSet<>());
        Production production = new Production();
        production.setCompany(company);
        company.setProduction(production);
        production.setProductionPlans(new ArrayList<>());
        Warehouse warehouse = new Warehouse();
        warehouse.setCompany(company);
        company.setWarehouse(warehouse);
        warehouse.setStocks(new ArrayList<>());
        Set<FinalProduct> finalProducts = sector.getFinalProducts();
        if (finalProducts != null && !finalProducts.isEmpty()) {
            int coreWorkforce = factory.getCoreWorkforce();
            int workforce = (int) (coreWorkforce / finalProducts.size());
            Set<IntermediateProduct> intermediateProducts = new HashSet<>();
            for (FinalProduct finalProduct : finalProducts) {
                ProductionPlan productionPlan = new ProductionPlan();
                productionPlan.setFinalProduct(finalProduct);
                productionPlan.setWorkforce(workforce);
                productionPlan.setProduction(production);
                production.getProductionPlans().add(productionPlan);
                Stock stock = new Stock();
                stock.setProduct(finalProduct);
                stock.setQuantity(0);
                stock.setWarehouse(warehouse);
                warehouse.getStocks().add(stock);
                for (Part part : finalProduct.getPartsList().getParts()) {
                    intermediateProducts.add(part.getIntermediateProduct());
                }
            }
            for (IntermediateProduct intermediateProduct : intermediateProducts) {
                Stock stock = new Stock();
                stock.setProduct(intermediateProduct);
                stock.setQuantity(0);
                stock.setWarehouse(warehouse);
                warehouse.getStocks().add(stock);
            }
        }
        company.setCooperators(new HashSet<>());
        Balance balance = new Balance();
        company.setBalance(balance);
        balance.setCompany(company);
        return company;
    }

    public boolean exists(String identifier, Economy economy) {
        if (identifier == null || economy == null) {
            return false;
        }
        TypedQuery<Company> query = em.createNamedQuery("Company.findByIdentifierAndEconomy", Company.class);
        query.setParameter("identifier", identifier);
        query.setParameter("economy", economy);
        return (query.getResultList().size() > 0);
    }
}
