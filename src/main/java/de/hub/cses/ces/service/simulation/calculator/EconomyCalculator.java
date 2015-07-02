package de.hub.cses.ces.service.simulation.calculator;

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
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.IntermediateProduct;
import de.hub.cses.ces.entity.product.Product;
import java.util.Date;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
public abstract class EconomyCalculator {

    /**
     *
     * @param economy
     * @param current
     * @throws NullPointerException
     */
    public abstract void init(Economy economy, Date current) throws NullPointerException;

    /**
     *
     * @param economy
     * @param current
     * @throws NullPointerException
     */
    public abstract void calculate(Economy economy, Date current) throws NullPointerException;

    /**
     *
     * @param product
     * @return
     */
    protected double getPrice(Product product) {
        if (IntermediateProduct.class.isAssignableFrom(product.getClass())) {
            return ((IntermediateProduct) product).getBasePurchasePrice();
        } else if (FinalProduct.class.isAssignableFrom(product.getClass())) {
            return ((FinalProduct) product).getBaseRetailPrice();
        }
        return 0d;
    }
}
