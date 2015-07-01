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
import de.hub.cses.ces.entity.company.accounting.WarehouseCharges;
import de.hub.cses.ces.entity.company.warehouse.Stock;
import de.hub.cses.ces.service.simulation.accounting.AccountingSystem;
import de.hub.cses.ces.service.simulation.accounting.BalanceTransactionAccounting;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@RequestScoped
public class WarehouseChargeCalculator extends CompanyCalculator {

    @Inject
    private AccountingSystem accountingSystem;

    @Inject
    private BalanceTransactionAccounting balanceTransactionAccounting;

    /**
     *
     * @param company
     * @param current
     * @throws NullPointerException
     */
    @Override
    public void calculate(Company company, Date current) throws NullPointerException {
        double overallWarehouseCharges = 0.0d;
        List<Stock> stocks = company.getWarehouse().getStocks();
        for (Stock stock : stocks) {
            int quantity = (int) stock.getQuantity();
            double warehouseChargesPerUnit = stock.getProduct().getWarehouseCharges();
            overallWarehouseCharges += ((double) quantity) * warehouseChargesPerUnit;
        }

        if (overallWarehouseCharges > 0.0d) {
            accountingSystem.addWarehouseCharges(company.getId(), overallWarehouseCharges);
        }

    }

    /**
     *
     * @param company
     * @param current
     * @throws NullPointerException
     */
    public void account(Company company, Date current) throws NullPointerException {
        double overallWarehouseCharges = accountingSystem.retrieveWarehouseCharges(company.getId());
        if (overallWarehouseCharges > 0.0d) {
            balanceTransactionAccounting.account(company.getBalance(), -overallWarehouseCharges, current, WarehouseCharges.class);
        }
    }

}
