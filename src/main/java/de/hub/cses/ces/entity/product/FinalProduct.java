package de.hub.cses.ces.entity.product;

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
import de.hub.cses.ces.entity.text.I18nText;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "product_final_tbl")
@JsonTypeName("finalProduct")
public class FinalProduct extends Product {

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parts_list")
    private PartsList partsList;
    @Column(name = "required_working_time")
    private double requiredWorkingTime;
    @Column(name = "base_retail_price")
    private double baseRetailPrice;

    /**
     *
     */
    public FinalProduct() {
        super();
    }

    /**
     *
     * @param id
     * @param description
     * @param warehouseCharges
     * @param partsList
     * @param requiredWorkingTime
     * @param baseRetailPrice
     */
    public FinalProduct(Long id, I18nText description, double warehouseCharges, PartsList partsList, double requiredWorkingTime, double baseRetailPrice) {
        super(id, description, warehouseCharges);
        this.partsList = partsList;
        this.requiredWorkingTime = requiredWorkingTime;
        this.baseRetailPrice = baseRetailPrice;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public PartsList getPartsList() {
        return partsList;
    }

    /**
     *
     * @param partsList
     */
    public void setPartsList(PartsList partsList) {
        this.partsList = partsList;
    }

    /**
     *
     * @return
     */
    @JsonProperty("requiredWorkingTime")
    public double getRequiredWorkingTime() {
        return requiredWorkingTime;
    }

    /**
     *
     * @param requiredWorkingTime
     */
    public void setRequiredWorkingTime(double requiredWorkingTime) {
        this.requiredWorkingTime = requiredWorkingTime;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public double getBaseRetailPrice() {
        return baseRetailPrice;
    }

    /**
     *
     * @param baseRetailPrice
     */
    public void setBaseRetailPrice(double baseRetailPrice) {
        this.baseRetailPrice = baseRetailPrice;
    }

}
