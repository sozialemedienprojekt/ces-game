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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "part_tbl")
public class Part extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "intermediate_product")
    private IntermediateProduct intermediateProduct;
    @Column(name = "quantity")
    private double quantity;

    /**
     *
     */
    public Part() {
        super();
    }

    /**
     *
     * @param id
     * @param intermediateProduct
     * @param quantity
     */
    public Part(Long id, IntermediateProduct intermediateProduct, double quantity) {
        super(id);
        this.intermediateProduct = intermediateProduct;
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    @JsonProperty("intermediateProduct")
    public IntermediateProduct getIntermediateProduct() {
        return intermediateProduct;
    }

    /**
     *
     * @param intermediateProduct
     */
    public void setIntermediateProduct(IntermediateProduct intermediateProduct) {
        this.intermediateProduct = intermediateProduct;
    }

    /**
     *
     * @return
     */
    @JsonProperty("quantity")
    public double getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
