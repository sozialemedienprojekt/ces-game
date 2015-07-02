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
import de.hub.cses.ces.entity.text.I18nText;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "market_tbl")
public class Market extends BaseEntity {

    @Column(name = "hourly_wage")
    private double hourlyWage;
    @Column(name = "working_hours_per_day")
    private double workingHoursPerDay;
    @OneToMany(mappedBy = "market", cascade = {CascadeType.ALL})
    private Set<Sector> sectors;
    @OneToMany(mappedBy = "market", cascade = {CascadeType.ALL})
    private Set<BaseDemand> baseDemands;
    @OneToMany(mappedBy = "market", cascade = {CascadeType.ALL})
    private Set<BaseSupply> baseSupplies;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "description")
    private I18nText description;

    /**
     *
     */
    public Market() {
        super();

    }

    /**
     *
     * @param id
     * @param hourlyWage
     * @param workingHoursPerDay
     * @param sectors
     * @param baseSupplies
     * @param baseDemands
     * @param description
     */
    public Market(Long id, double hourlyWage, double workingHoursPerDay, Set<Sector> sectors, Set<BaseSupply> baseSupplies, Set<BaseDemand> baseDemands, I18nText description) {
        super(id);
        this.hourlyWage = hourlyWage;
        this.workingHoursPerDay = workingHoursPerDay;
        this.sectors = sectors;
        this.baseSupplies = baseSupplies;
        this.baseDemands = baseDemands;
        this.description = description;
    }

    /**
     *
     * @return
     */
    public double getHourlyWage() {
        return hourlyWage;
    }

    /**
     *
     * @param hourlyWage
     */
    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    /**
     *
     * @return
     */
    public double getWorkingHoursPerDay() {
        return workingHoursPerDay;
    }

    /**
     *
     * @param workingHoursPerDay
     */
    public void setWorkingHoursPerDay(double workingHoursPerDay) {
        this.workingHoursPerDay = workingHoursPerDay;
    }

    /**
     *
     * @return
     */
    public Set<Sector> getSectors() {
        return sectors;
    }

    /**
     *
     * @param sectors
     */
    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
    }

    /**
     *
     * @return
     */
    public Set<BaseDemand> getBaseDemands() {
        return baseDemands;
    }

    /**
     *
     * @param baseDemands
     */
    public void setBaseDemands(Set<BaseDemand> baseDemands) {
        this.baseDemands = baseDemands;
    }

    /**
     *
     * @return
     */
    public Set<BaseSupply> getBaseSupplies() {
        return baseSupplies;
    }

    /**
     *
     * @param baseSupplies
     */
    public void setBaseSupplies(Set<BaseSupply> baseSupplies) {
        this.baseSupplies = baseSupplies;
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
