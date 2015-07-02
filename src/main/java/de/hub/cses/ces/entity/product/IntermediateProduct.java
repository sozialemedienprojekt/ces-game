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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonTypeName;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "product_intermediate_tbl")
@JsonTypeName("intermediateProduct")
public class IntermediateProduct extends Product {

    @Column(name = "base_purchase_price", insertable = true, updatable = false)
    private double basePurchasePrice;

    /**
     *
     */
    public IntermediateProduct() {
        super();
    }

    /**
     *
     * @param id
     * @param identifier
     * @param warehouseCharges
     * @param basePurchasePrice
     */
    public IntermediateProduct(Long id, I18nText identifier, double warehouseCharges, double basePurchasePrice) {
        super(id, identifier, warehouseCharges);
        this.basePurchasePrice = basePurchasePrice;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public double getBasePurchasePrice() {
        return basePurchasePrice;
    }

    /**
     *
     * @param basePurchasePrice
     */
    public void setBasePurchasePrice(double basePurchasePrice) {
        this.basePurchasePrice = basePurchasePrice;
    }
}
