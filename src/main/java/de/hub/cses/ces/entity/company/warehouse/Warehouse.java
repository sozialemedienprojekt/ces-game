package de.hub.cses.ces.entity.company.warehouse;

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
@Table(name = "company_warehouse_tbl")
public class Warehouse extends BaseEntity {

    @OneToOne(mappedBy = "warehouse")
    private Company company;
    @OneToMany(mappedBy = "warehouse", cascade = {CascadeType.ALL})
    private List<Stock> stocks;

    /**
     *
     */
    public Warehouse() {
        super();
    }

    /**
     *
     * @param id
     * @param company
     * @param stocks
     */
    public Warehouse(Long id, Company company, List<Stock> stocks) {
        super(id);
        this.company = company;
        this.stocks = stocks;
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
    public List<Stock> getStocks() {
        return stocks;
    }

    /**
     *
     * @param stocks
     */
    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
