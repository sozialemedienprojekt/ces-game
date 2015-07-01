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
import de.hub.cses.ces.entity.company.Company;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "production_tbl")
public class Production extends BaseEntity {

    @OneToOne(mappedBy = "production")
    private Company company;
    @OneToMany(mappedBy = "production", cascade = {CascadeType.ALL})
    private List<ProductionPlan> productionPlans;

    /**
     *
     */
    public Production() {

    }

    /**
     *
     * @param id
     * @param productionPlans
     */
    public Production(Long id, List<ProductionPlan> productionPlans) {
        super(id);
        this.productionPlans = productionPlans;
    }

    /**
     *
     * @return
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * @param company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     *
     * @return
     */
    public List<ProductionPlan> getProductionPlans() {
        return productionPlans;
    }

    /**
     *
     * @param productionPlans
     */
    public void setProductionPlans(List<ProductionPlan> productionPlans) {
        this.productionPlans = productionPlans;
    }

}
