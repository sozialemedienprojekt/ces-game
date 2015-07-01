package de.hub.cses.ces.service.simulation.accounting;

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
/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
public class Account {

    private double wages = 0.0d;
    private double warehouseCharges = 0.0d;

    /**
     *
     */
    public Account() {
    }

    /**
     *
     * @param wages
     */
    public void addWages(double wages) {
        this.wages += wages;
    }

    /**
     *
     * @return
     */
    public double retrieveWages() {
        double d = wages;
        wages = 0.0d;
        return d;
    }

    /**
     *
     * @param warehouseCharges
     */
    public void addWarehouseCharges(double warehouseCharges) {
        this.warehouseCharges += warehouseCharges;
    }

    /**
     *
     * @return
     */
    public double retrieveWarehouseCharges() {
        double d = warehouseCharges;
        warehouseCharges = 0.0d;
        return d;
    }

}
