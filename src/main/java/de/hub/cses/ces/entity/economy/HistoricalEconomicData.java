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
import de.hub.cses.ces.entity.BaseEntity;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "economic_data_historical_tbl")
public class HistoricalEconomicData extends BaseEntity {

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "economic_data_historical_dynamic_supply_mapping_tbl",
            joinColumns = @JoinColumn(name = "historial_economic_data"),
            inverseJoinColumns = @JoinColumn(name = "dynamic_supply"))
    private Collection<DynamicSupply> dynamicSupplies;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "economic_data_historical_dynamic_demand_mapping_tbl",
            joinColumns = @JoinColumn(name = "historical_economic_data"),
            inverseJoinColumns = @JoinColumn(name = "dynamic_demand"))
    private Collection<DynamicDemand> dynamicDemands;

    /**
     *
     */
    public HistoricalEconomicData() {
        super();
    }

    /**
     *
     * @param dynamicSupplies
     * @param dynamicDemands
     */
    public HistoricalEconomicData(Collection<DynamicSupply> dynamicSupplies, Collection<DynamicDemand> dynamicDemands) {
        super();
        this.dynamicSupplies = dynamicSupplies;
        this.dynamicDemands = dynamicDemands;
    }

    /**
     *
     * @return
     */
    @JsonProperty("supplies")
    public Collection<DynamicSupply> getDynamicSupplies() {
        return dynamicSupplies;
    }

    /**
     *
     * @param dynamicSupplies
     */
    public void setDynamicSupplies(Collection<DynamicSupply> dynamicSupplies) {
        this.dynamicSupplies = dynamicSupplies;
    }

    /**
     *
     * @return
     */
    @JsonProperty("demands")
    public Collection<DynamicDemand> getDynamicDemands() {
        return dynamicDemands;
    }

    /**
     *
     * @param dynamicDemands
     */
    public void setDynamicDemands(Collection<DynamicDemand> dynamicDemands) {
        this.dynamicDemands = dynamicDemands;
    }

}
