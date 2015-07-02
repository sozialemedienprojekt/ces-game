package de.hub.cses.ces.entity.market;

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
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "market_base_supply_tbl")
public class BaseSupply extends Supply {

    @ManyToOne
    @JoinColumn(name = "market")
    private Market market;

    /**
     *
     */
    public BaseSupply() {
        super();
    }

    /**
     *
     * @param id
     * @param product
     * @param quantity
     */
    public BaseSupply(Long id, Product product, int quantity) {
        super(id, product, quantity);
    }

    /**
     *
     * @param id
     * @param product
     * @param quantity
     * @param market
     */
    public BaseSupply(Long id, Product product, int quantity, Market market) {
        super(id, product, quantity);
        this.market = market;
    }

    /**
     *
     * @return
     */
    public Market getMarket() {
        return market;
    }

    /**
     *
     * @param market
     */
    public void setMarket(Market market) {
        this.market = market;
    }

}
