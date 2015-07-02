package de.hub.cses.ces.service.simulation;

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
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.accounting.Balance;
import de.hub.cses.ces.entity.company.accounting.InvestmentCosts;
import de.hub.cses.ces.entity.company.accounting.SeedCapital;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.service.simulation.accounting.BalanceTransactionAccounting;
import de.hub.cses.ces.service.simulation.calculator.DynamicDemandCalculator;
import de.hub.cses.ces.service.simulation.calculator.DynamicSupplyCalculator;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class SimulationInitializer {

    @EJB
    private GameFacade gameFacade;

    @Inject
    private BalanceTransactionAccounting balanceTransactionAccounting;

    @Inject
    private DynamicSupplyCalculator dynamicSupplyCalculator;

    @Inject
    private DynamicDemandCalculator dynamicDemandCalculator;

    /**
     *
     * @param gameId
     */
    public void initialize(Long gameId) {
        Game game = gameFacade.find(gameId, LockModeType.PESSIMISTIC_WRITE);
        Date today = game.getStartAt();
        game.setToday(new Date(today.getTime()));
        game.setGameStatus(GameStatus.PAUSED);
        double seedCapital = game.getSeedCapital();
        Economy economy = game.getEconomy();
        dynamicSupplyCalculator.init(economy, today);
        dynamicDemandCalculator.init(economy, today);
        economy.getCompanies().stream().forEach((Company company) -> {
            Balance balance = company.getBalance();
            balanceTransactionAccounting.account(balance, seedCapital, today, SeedCapital.class);
            balanceTransactionAccounting.account(balance, -company.getFactory().getBuildingCosts(), today, InvestmentCosts.class);
        });
    }

}
