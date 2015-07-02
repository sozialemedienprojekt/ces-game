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
import de.hub.cses.ces.entity.company.warehouse.Stock;
import de.hub.cses.ces.entity.company.warehouse.StockTransaction;
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@FacesComponent("StocksPanelComponent")
public class StocksPanelComponent extends UINamingContainer {

    private Stock stock;

    /**
     *
     */
    public StocksPanelComponent() {
        super();
    }

    private Warehouse getWarehouse() {
        return (Warehouse) getAttributes().get("warehouse");
    }

    /**
     *
     * @return
     */
    public List<Stock> getStocks() {
        List<Stock> stocks = new ArrayList<>();
        Warehouse warehouse = getWarehouse();
        if (warehouse != null) {
            List<Stock> stocksSet = warehouse.getStocks();
            stocks.addAll(stocksSet);
            Collections.sort(stocks, (Stock stock1, Stock stock2) -> {
                try {
                    return Long.compare(stock1.getProduct().getId(), stock2.getProduct().getId());
                } catch (NullPointerException npe) {

                }
                return 0;
            });
        }
        return stocks;
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

    /**
     *
     * @return
     */
    public double getOverallWarehouseCharges() {
        double charges = 0d;
        if (stock != null) {
            charges = stock.getQuantity() * stock.getProduct().getWarehouseCharges();
        }
        return charges;
    }

    /**
     *
     * @return
     */
    public Collection<StockTransaction> getStockTransactions() {
        List<StockTransaction> stockTransactions = new ArrayList<>();
        if (stock != null) {
            stockTransactions.addAll(stock.getTransactions());
            Collections.sort(stockTransactions, (StockTransaction t1, StockTransaction t2) -> {
                if (t1.getPosted().equals(t2.getPosted())) {
                    return 0;
                } else if (t1.getPosted().before(t2.getPosted())) {
                    return 1;
                }
                return -1;
            });
        }
        return stockTransactions;
    }

}
