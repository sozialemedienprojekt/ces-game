package de.hub.cses.ces.util;

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
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@RequestScoped
public class DoubleUtil {

    /**
     *
     */
    public DoubleUtil() {

    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     *
     * @param min Minimum value
     * @param max Maximum value. Must be greater than min.
     * @return Double between min and max, inclusive.
     * @see java.util.Random#nextDouble()
     */
    public double randDouble(double min, double max) {
        double r = Math.random();
        if (r < 0.5) {
            return ((1 - Math.random()) * (max - min) + min);
        }
        return (Math.random() * (max - min) + min);
    }

    /**
     *
     * @param value
     * @param places
     * @return
     */
    public double round(double value, int places) {
        return round(value, places, RoundingMode.HALF_UP);
    }

    /**
     *
     * @param value
     * @param places
     * @param roundingMode
     * @return
     */
    public double round(double value, int places, RoundingMode roundingMode) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, roundingMode);
        return bd.doubleValue();
    }
}
