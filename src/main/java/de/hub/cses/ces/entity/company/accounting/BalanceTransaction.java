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
import de.hub.cses.ces.entity.economy.Transaction;
import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "accounting_balance_transaction_tbl")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "transaction_type")
public abstract class BalanceTransaction extends Transaction {

    @ManyToOne
    @JoinColumn(name = "balance")
    private Balance balance;

    /**
     *
     */
    protected BalanceTransaction() {
        super();
    }

    /**
     *
     * @param amountPosted
     * @param posted
     * @param balance
     */
    protected BalanceTransaction(double amountPosted, Date posted, Balance balance) {
        super(amountPosted, posted);
        this.balance = balance;
    }

    /**
     *
     * @return
     */
    public Balance getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(Balance balance) {
        this.balance = balance;
    }

}
