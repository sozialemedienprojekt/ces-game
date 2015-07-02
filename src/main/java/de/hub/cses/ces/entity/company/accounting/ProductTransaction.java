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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "accounting_balance_transaction_product_tbl")
public abstract class ProductTransaction extends BalanceTransaction {

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
    @Column(name = "pricePerUnit")
    private double pricePerUnit;
    @Column(name = "quantity")
    private int quantity;

    /**
     *
     */
    public ProductTransaction() {
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
    protected ProductTransaction(double amountPosted, Date posted, Balance balance, Product product, double pricePerUnit, int quantity) {
        super(amountPosted, posted, balance);
        this.product = product;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public Product getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     *
     * @return
     */
    public double getPricePerUnit() {
        return pricePerUnit;
    }

    /**
     *
     * @param pricePerUnit
     */
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    /**
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
