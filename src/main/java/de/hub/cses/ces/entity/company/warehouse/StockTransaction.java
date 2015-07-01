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
import de.hub.cses.ces.entity.economy.Transaction;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "company_warehouse_stock_transaction_tbl")
public class StockTransaction extends Transaction {

    @ManyToOne
    @JoinColumn(name = "stock")
    private Stock stock;

    /**
     *
     */
    public StockTransaction() {
        super();
    }

    /**
     *
     * @param amountPosted
     * @param posted
     * @param stock
     */
    public StockTransaction(double amountPosted, Date posted, Stock stock) {
        super(amountPosted, posted);
        this.stock = stock;
    }

    /**
     *
     * @return
     */
    public Stock getStock() {
        return stock;
    }

    /**
     *
     * @param stock
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
