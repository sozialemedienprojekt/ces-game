package de.hub.cses.ces.entity.production;

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
import de.hub.cses.ces.entity.product.FinalProduct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "production_plan_tbl")
public class ProductionPlan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "production")
    private Production production;
    @ManyToOne
    @JoinColumn(name = "final_product")
    private FinalProduct finalProduct;
    @Column(name = "workforce")
    private int workforce;

    /**
     *
     */
    public ProductionPlan() {
        super();
    }

    /**
     *
     * @param id
     * @param production
     * @param finalProduct
     * @param workforce
     */
    public ProductionPlan(Long id, Production production, FinalProduct finalProduct, int workforce) {
        super(id);
        this.production = production;
        this.finalProduct = finalProduct;
        this.workforce = workforce;
    }

    /**
     *
     * @return
     */
    public Production getProduction() {
        return production;
    }

    /**
     *
     * @param production
     */
    public void setProduction(Production production) {
        this.production = production;
    }

    /**
     *
     * @param id
     * @param finalProduct
     * @param workForce
     */
    public ProductionPlan(Long id, FinalProduct finalProduct, int workForce) {
        super(id);
        this.finalProduct = finalProduct;
        this.workforce = workForce;
    }

    /**
     *
     * @return
     */
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
    public int getWorkforce() {
        return workforce;
    }

    /**
     *
     * @param workforce
     */
    public void setWorkforce(int workforce) {
        if (workforce >= 0) {
            this.workforce = workforce;
        }
    }
}
