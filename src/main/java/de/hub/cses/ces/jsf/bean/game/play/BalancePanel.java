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
import de.hub.cses.ces.entity.company.accounting.BalanceTransaction;
import de.hub.cses.ces.entity.company.accounting.Disposal;
import de.hub.cses.ces.entity.company.accounting.Purchase;
import de.hub.cses.ces.entity.text.I18nText;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("BalancePanel")
@ViewScoped
public class BalancePanel implements Serializable {

    private BalanceTransaction selectedTransaction;

    /**
     *
     */
    public BalancePanel() {

    }

    /**
     *
     * @return
     */
    public BalanceTransaction getSelectedTransaction() {
        return selectedTransaction;
    }

    /**
     *
     * @param selectedTransaction
     */
    public void setSelectedTransaction(BalanceTransaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }

    /**
     *
     * @return
     */
    public I18nText getProductDescription() {
        try {
            if (Disposal.class.isAssignableFrom(selectedTransaction.getClass())) {
                return ((Disposal) selectedTransaction).getProduct().getDescription();
            } else if (Purchase.class.isAssignableFrom(selectedTransaction.getClass())) {
                return ((Purchase) selectedTransaction).getProduct().getDescription();
            }
        } catch (NullPointerException npe) {

        }
        return null;
    }
}
