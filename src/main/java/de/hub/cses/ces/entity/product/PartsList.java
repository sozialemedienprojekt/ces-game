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
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "parts_list_tbl")
public class PartsList extends BaseEntity {

    @OneToOne(mappedBy = "partsList")
    private FinalProduct finalProduct;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parts_list")
    private Set<Part> parts;

    /**
     *
     */
    public PartsList() {
        super();
    }

    /**
     *
     * @param id
     * @param parts
     */
    public PartsList(Long id, Set<Part> parts) {
        super(id);
        this.parts = parts;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public FinalProduct getFinalProduct() {
        return finalProduct;
    }

    /**
     *
     * @param finalProduct
     */
    public void setFinalProduct(FinalProduct finalProduct) {
        this.finalProduct = finalProduct;
    }

    /**
     *
     * @return
     */
    @JsonProperty("parts")
    public Set<Part> getParts() {
        return parts;
    }

    /**
     *
     * @param parts
     */
    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
