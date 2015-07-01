package de.hub.cses.ces.entity.company.accounting;

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
import de.hub.cses.ces.entity.BaseEntity;
import de.hub.cses.ces.entity.company.Company;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "accounting_balance_tbl")
public class Balance extends BaseEntity {

    @OneToOne(mappedBy = "balance")
    private Company company;
    @Column(name = "amount")
    private double amount;
    @OneToMany(mappedBy = "balance", cascade = {CascadeType.ALL})
    private Set<BalanceTransaction> transactions;

    /**
     *
     */
    public Balance() {
        super();
    }

    /**
     *
     * @param company
     * @param amount
     * @param transactions
     */
    public Balance(Company company, double amount, Set<BalanceTransaction> transactions) {
        super();
        this.company = company;
        this.amount = amount;
        this.transactions = transactions;
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
    public double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public Set<BalanceTransaction> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     */
    public void setTransactions(Set<BalanceTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     *
     * @param transaction
     * @throws NullPointerException
     */
    public void addTransaction(BalanceTransaction transaction) throws NullPointerException {
        transactions.add(transaction);
        amount += transaction.getAmountPosted();
    }
}
