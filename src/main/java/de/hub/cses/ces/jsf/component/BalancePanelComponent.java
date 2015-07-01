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
import de.hub.cses.ces.entity.company.accounting.Balance;
import de.hub.cses.ces.entity.company.accounting.BalanceTransaction;
import de.hub.cses.ces.entity.company.accounting.Disposal;
import de.hub.cses.ces.entity.company.accounting.ProductTransaction;
import de.hub.cses.ces.entity.company.accounting.Purchase;
import de.hub.cses.ces.entity.text.I18nText;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@FacesComponent("BalancePanelComponent")
public class BalancePanelComponent extends UINamingContainer {

    private static final Logger logger = Logger.getLogger(BalancePanelComponent.class.getName());

    private BalanceTransaction transaction;

    /**
     *
     */
    public BalancePanelComponent() {
        super();
    }

    /**
     *
     * @return
     */
    public List<BalanceTransaction> getBalanceTransactions() {
        List<BalanceTransaction> balanceTransactions = new ArrayList<>();
        Balance balance = getBalance();
        if (balance != null) {
            Set<BalanceTransaction> balanceTransactionSet = balance.getTransactions();
            balanceTransactions.addAll(balanceTransactionSet);
            Collections.sort(balanceTransactions, (BalanceTransaction t1, BalanceTransaction t2) -> {
                if (t1.getPosted().equals(t2.getPosted())) {
                    return 0;
                } else if (t1.getPosted().before(t2.getPosted())) {
                    return 1;
                }
                return -1;
            });
        }
        return balanceTransactions;
    }

    private Balance getBalance() {
        return (Balance) getAttributes().get("balance");
    }

    /**
     *
     * @return
     */
    public List getList() {
        return new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public BalanceTransaction getTransaction() {
        return transaction;
    }

    /**
     *
     * @param transaction
     */
    public void setTransaction(BalanceTransaction transaction) {
        this.transaction = transaction;
    }

    /**
     *
     * @return
     */
    public I18nText getProductDescription() {
        if (transaction != null) {
            if (Disposal.class.isAssignableFrom(transaction.getClass()) || (Purchase.class.isAssignableFrom(transaction.getClass()))) {
                return ((ProductTransaction) transaction).getProduct().getDescription();
            }
        }
        return null;
    }

}
