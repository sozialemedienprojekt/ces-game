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
import de.hub.cses.ces.entity.BaseEntityWithIdentifier;
import de.hub.cses.ces.entity.company.accounting.Balance;
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.production.Production;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "company_tbl")
public class Company extends BaseEntityWithIdentifier {

    @ManyToOne
    @JoinColumn(name = "economy")
    private Economy economy;
    @OneToMany(mappedBy = "company")
    private Set<Cooperator> cooperators;
    @ManyToOne
    @JoinColumn(name = "sector")
    private Sector sector;
    @ManyToOne
    @JoinColumn(name = "factory")
    private Factory factory;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "production")
    private Production production;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "balance")
    private Balance balance;

    /**
     *
     */
    public Company() {
        super();
    }

    /**
     *
     * @param id
     */
    public Company(Long id) {
        super(id);
    }

    /**
     *
     * @param id
     * @param identifier
     * @param cooperators
     * @param factory
     * @param production
     * @param balance
     * @param warehouse
     */
    public Company(Long id, String identifier, Set<Cooperator> cooperators, Factory factory, Production production, Balance balance, Warehouse warehouse) {
        super(id, identifier);
        this.cooperators = cooperators;
        this.factory = factory;
        this.balance = balance;
        this.warehouse = warehouse;
    }

    /**
     *
     * @return
     */
    public Economy getEconomy() {
        return economy;
    }

    /**
     *
     * @param economy
     */
    public void setEconomy(Economy economy) {
        this.economy = economy;
    }

    /**
     *
     * @return
     */
    public Sector getSector() {
        return sector;
    }

    /**
     *
     * @param sector
     */
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    /**
     *
     * @param cooperators
     */
    public void setCooperators(Set<Cooperator> cooperators) {
        this.cooperators = cooperators;
    }

    /**
     *
     * @return
     */
    public Set<Cooperator> getCooperators() {
        return cooperators;
    }

    /**
     *
     * @return
     */
    public Factory getFactory() {
        return factory;
    }

    /**
     *
     * @param factory
     */
    public void setFactory(Factory factory) {
        this.factory = factory;
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
     * @param warehouse
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     *
     * @return
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     *
     * @return
     */
    public Balance getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(Balance balance) {
        this.balance = balance;
    }

}
