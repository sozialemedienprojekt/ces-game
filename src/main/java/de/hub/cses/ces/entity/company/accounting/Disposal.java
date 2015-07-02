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
import de.hub.cses.ces.entity.product.Product;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "accounting_balance_transaction_product_disposal_tbl")
@DiscriminatorValue("Disposal")
public class Disposal extends ProductTransaction {

    /**
     *
     */
    public Disposal() {
        super();
    }

    /**
     *
     * @param amountPosted
     * @param posted
     * @param balance
     * @param product
     * @param pricePerUnit
     * @param quantity
     */
    public Disposal(double amountPosted, Date posted, Balance balance, Product product, double pricePerUnit, int quantity) {
        super(amountPosted, posted, balance, product, pricePerUnit, quantity);
    }

}
