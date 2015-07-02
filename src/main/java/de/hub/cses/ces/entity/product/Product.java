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
import de.hub.cses.ces.entity.BaseEntity;
import de.hub.cses.ces.entity.text.I18nText;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "product_tbl")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FinalProduct.class, name = "finalProduct"),
    @JsonSubTypes.Type(value = IntermediateProduct.class, name = "intermediateProduct")})
public abstract class Product extends BaseEntity {

    @Column(name = "warehouse_charges")
    private double warehouseCharges;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "description")
    private I18nText description;

    /**
     *
     */
    protected Product() {
        super();
    }

    /**
     *
     * @param id
     * @param description
     * @param warehouseCharges
     */
    protected Product(Long id, I18nText description, double warehouseCharges) {
        super(id);
        this.description = description;
        this.warehouseCharges = warehouseCharges;
    }

    /**
     *
     * @return
     */
    @JsonProperty("warehouseCharges")
    public double getWarehouseCharges() {
        return warehouseCharges;
    }

    /**
     *
     * @param warehouseCharges
     */
    public void setWarehouseCharges(double warehouseCharges) {
        this.warehouseCharges = warehouseCharges;
    }

    /**
     *
     * @return
     */
    @JsonProperty("description")
    public I18nText getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(I18nText description) {
        this.description = description;
    }

}
