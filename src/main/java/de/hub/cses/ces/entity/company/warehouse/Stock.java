package de.hub.cses.ces.entity.company.warehouse;

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
import de.hub.cses.ces.entity.product.Product;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "company_warehouse_stock_tbl")
public class Stock extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
    @OneToMany(mappedBy = "stock", cascade = {CascadeType.ALL})
    private Set<StockTransaction> transactions;
    @Column(name = "quantity")
    @Min(value = 0)
    private double quantity;

    /**
     *
     */
    public Stock() {
        super();
    }

    /**
     *
     * @param id
     * @param warehouse
     * @param product
     * @param amount
     */
    public Stock(Long id, Warehouse warehouse, Product product, int amount) {
        super(id);
        this.warehouse = warehouse;
        this.product = product;
        this.quantity = amount;
    }

    /**
     *
     * @return
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     *
     * @param warehouse
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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
    public double getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public Set<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     */
    public void setTransactions(Set<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     *
     * @param transaction
     * @throws NullPointerException
     */
    public void addTransaction(StockTransaction transaction) throws NullPointerException {
        transactions.add(transaction);
        quantity += transaction.getAmountPosted();
    }

}
