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
import de.hub.cses.ces.entity.company.warehouse.Stock;
import de.hub.cses.ces.entity.company.warehouse.StockTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("StocksPanel")
@ViewScoped
public class StocksPanel implements Serializable {

    private Stock selectedStock;

    private boolean transactionDialogVisible = false;
    private boolean detailDialogVisible = false;

    /**
     *
     * @return
     */
    public Stock getSelectedStock() {
        return selectedStock;
    }

    /**
     *
     * @param selectedStock
     */
    public void setSelectedStock(Stock selectedStock) {
        this.selectedStock = selectedStock;
    }

    /**
     *
     * @return
     */
    public double getOverallWarehouseCharges() {
        if (selectedStock == null) {
            return 0d;
        }
        return selectedStock.getProduct().getWarehouseCharges() * selectedStock.getQuantity();
    }

    /**
     *
     * @return
     */
    public boolean isTransactionDialogVisible() {
        return transactionDialogVisible;
    }

    /**
     *
     */
    public void closeTransactionDialog() {
        this.transactionDialogVisible = false;
    }

    /**
     *
     */
    public void openTransactionDialog() {
        this.transactionDialogVisible = true;
    }

    /**
     *
     * @return
     */
    public boolean isDetailDialogVisible() {
        return detailDialogVisible;
    }

    /**
     *
     */
    public void closeDetailDialog() {
        this.detailDialogVisible = false;
    }

    /**
     *
     */
    public void openDetailDialog() {
        this.detailDialogVisible = true;
    }

    /**
     *
     * @return
     */
    public List<StockTransaction> getStockTransactions() {
        if (selectedStock == null) {
            return null;
        }
        List<StockTransaction> stockTransactions = new ArrayList<>();
        stockTransactions.addAll(selectedStock.getTransactions());
        Collections.sort(stockTransactions);
        Collections.reverse(stockTransactions);
        return stockTransactions;
    }

}
