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
import de.hub.cses.ces.entity.market.Demand;
import de.hub.cses.ces.entity.product.Product;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "economy_dynamic_demand_tbl")
@NamedQueries({
    @NamedQuery(name = "DynamicDemand.findByProduct", query = "SELECT d FROM DynamicDemand d WHERE d.product = :product ORDER BY d.calculated DESC")
})
public class DynamicDemand extends Demand {

    @Column(name = "available_quantity", insertable = true, updatable = true, nullable = false)
    @Min(value = 0)
    private int availableQuantity;
    @Column(name = "price_per_unit", insertable = true, updatable = true, nullable = false)
    private double pricePerUnit;
    @Column(name = "calculated", insertable = true, updatable = true, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date calculated;

    /**
     *
     */
    public DynamicDemand() {
        super();
    }

    /**
     *
     * @param id
     * @param product
     * @param quantity
     * @param availableQuantity
     * @param pricePerUnit
     * @param calculated
     */
    public DynamicDemand(Long id, Product product, int quantity, int availableQuantity, double pricePerUnit, Date calculated) {
        super(id, product, quantity);
        this.availableQuantity = availableQuantity;
        this.pricePerUnit = pricePerUnit;
        this.calculated = calculated;
    }

    /**
     *
     * @return
     */
    @JsonProperty("availableQuantity")
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     *
     * @param availableQuantity
     */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     *
     * @return
     */
    @JsonProperty("pricePerUnit")
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
    @JsonProperty("calculated")
    public Date getCalculated() {
        return calculated;
    }

    /**
     *
     * @param calculated
     */
    public void setCalculated(Date calculated) {
        this.calculated = calculated;
    }

}
