package de.hub.cses.ces.entity.company;

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
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "factory_tbl")
public class Factory extends BaseEntity {

    @Column(name = "building_costs")
    private double buildingCosts;
    @Column(name = "overhead")
    private double overhead;
    @Column(name = "core_workforce")
    private int coreWorkforce;
    @Column(name = "max_workforce")
    private int maxWorkforce;
    @Column(name = " supply_factor")
    private double supplyFactor;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "description")
    private I18nText description;

    /**
     *
     */
    public Factory() {
        super();
    }

    /**
     *
     * @param id
     * @param buildingCosts
     * @param overhead
     * @param supplyFactor
     * @param coreWorkforce
     * @param maxWorkforce
     * @param description
     */
    public Factory(Long id, double buildingCosts, double overhead, double supplyFactor, int coreWorkforce, int maxWorkforce, I18nText description) {
        super(id);
        this.buildingCosts = buildingCosts;
        this.overhead = overhead;
        this.supplyFactor = supplyFactor;
        this.coreWorkforce = coreWorkforce;
        this.maxWorkforce = maxWorkforce;
        this.description = description;
    }

    /**
     *
     * @return
     */
    public double getBuildingCosts() {
        return buildingCosts;
    }

    /**
     *
     * @param buildingCosts
     */
    public void setBuildingCosts(double buildingCosts) {
        this.buildingCosts = buildingCosts;
    }

    /**
     *
     * @return
     */
    public double getOverhead() {
        return overhead;
    }

    /**
     *
     * @param overhead
     */
    public void setOverhead(double overhead) {
        this.overhead = overhead;
    }

    /**
     *
     * @return
     */
    public double getSupplyFactor() {
        return supplyFactor;
    }

    /**
     *
     * @param supplyFactor
     */
    public void setSupplyFactor(double supplyFactor) {
        this.supplyFactor = supplyFactor;
    }

    /**
     *
     * @return
     */
    public int getCoreWorkforce() {
        return coreWorkforce;
    }

    /**
     *
     * @param coreWorkforce
     */
    public void setCoreWorkforce(int coreWorkforce) {
        this.coreWorkforce = coreWorkforce;
    }

    /**
     *
     * @return
     */
    public int getMaxWorkforce() {
        return maxWorkforce;
    }

    /**
     *
     * @param maxWorkforce
     */
    public void setMaxWorkforce(int maxWorkforce) {
        this.maxWorkforce = maxWorkforce;
    }

    /**
     *
     * @return
     */
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
