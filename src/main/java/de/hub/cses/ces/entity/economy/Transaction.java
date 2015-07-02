package de.hub.cses.ces.entity.economy;

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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@MappedSuperclass
public abstract class Transaction extends BaseEntity {

    @Column(name = "booking_value")
    private double amountPosted;
    @Column(name = "booking_date")
    @Temporal(TemporalType.DATE)
    private Date posted;

    /**
     *
     */
    protected Transaction() {
        super();
    }

    /**
     *
     * @param amountPosted
     * @param posted
     */
    protected Transaction(double amountPosted, Date posted) {
        super();
        this.amountPosted = amountPosted;
        this.posted = posted;
    }

    /**
     *
     * @return
     */
    public double getAmountPosted() {
        return amountPosted;
    }

    /**
     *
     * @param amountPosted
     */
    public void setAmountPosted(double amountPosted) {
        this.amountPosted = amountPosted;
    }

    /**
     *
     * @return
     */
    public Date getPosted() {
        return posted;
    }

    /**
     *
     * @param posted
     */
    public void setPosted(Date posted) {
        this.posted = posted;
    }
}
