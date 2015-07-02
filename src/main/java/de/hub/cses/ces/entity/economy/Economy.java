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
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.market.Market;
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
@Table(name = "economy_tbl")
public class Economy extends BaseEntity {

    @OneToOne(mappedBy = "economy")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "market")
    private Market market;
    @OneToMany(mappedBy = "economy", cascade = {CascadeType.ALL})
    private Set<Company> companies;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "economic_data")
    private EconomicData economicData;

    /**
     *
     */
    public Economy() {
        super();
    }

    /**
     *
     * @param game
     * @param market
     * @param companies
     * @param economicData
     */
    public Economy(Game game, Market market, Set<Company> companies, EconomicData economicData) {
        super();
        this.game = game;
        this.market = market;
        this.companies = companies;
        this.economicData = economicData;
    }

    /**
     *
     * @return
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     *
     * @return
     */
    public Market getMarket() {
        return market;
    }

    /**
     *
     * @param market
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     *
     * @return
     */
    public Set<Company> getCompanies() {
        return companies;
    }

    /**
     *
     * @param companies
     */
    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    /**
     *
     * @return
     */
    public EconomicData getEconomicData() {
        return economicData;
    }

    /**
     *
     * @param economicData
     */
    public void setEconomicData(EconomicData economicData) {
        this.economicData = economicData;
    }

}
