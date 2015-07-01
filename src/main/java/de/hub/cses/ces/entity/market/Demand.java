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
import de.hub.cses.ces.entity.BaseEntity;
import de.hub.cses.ces.entity.product.Product;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@MappedSuperclass
public class Demand extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product", insertable = true, updatable = false)
    private Product product;
    @Column(name = "quantity", insertable = true, updatable = true, nullable = false)
    @Min(value = 0)
    private int quantity;

    /**
     *
     */
    protected Demand() {
        super();
    }

    /**
     *
     * @param id
     * @param product
     * @param quantity
     */
    protected Demand(Long id, Product product, int quantity) {
        super(id);
        this.product = product;
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    @JsonProperty("productId")
    public Long getProductId() {
        return (product != null) ? product.getId() : null;
    }

    /**
     *
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     *
     * @return
     */
    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
