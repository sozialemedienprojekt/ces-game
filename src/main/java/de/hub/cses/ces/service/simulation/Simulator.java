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
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.jsf.config.GamePlayComponent;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.service.simulation.calculator.DynamicDemandCalculator;
import de.hub.cses.ces.service.simulation.calculator.DynamicSupplyCalculator;
import de.hub.cses.ces.service.simulation.calculator.OverheadCalculator;
import de.hub.cses.ces.service.simulation.calculator.ProductionCalculator;
import de.hub.cses.ces.service.simulation.calculator.WageCalculator;
import de.hub.cses.ces.service.simulation.calculator.WarehouseChargeCalculator;
import de.hub.cses.ces.util.CalendarUtil;
import de.hub.cses.ces.util.ComponentUpdateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class Simulator {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private GameFacade gameFacade;

    @Inject
    private ProductionCalculator productionCalculator;

    @Inject
    private WageCalculator wageCalculator;

    @Inject
    private OverheadCalculator overheadCalculator;

    @Inject
    private WarehouseChargeCalculator warehouseChargesCalculator;

    @Inject
    private DynamicSupplyCalculator supplyCalculator;

    @Inject
    private DynamicDemandCalculator demandCalculator;

    @Inject
    private CalendarUtil calendarUtil;

    @Inject
    private ComponentUpdateUtil componentUpdateUtil;

    /**
     *
     * @param gameId
     */
    @Asynchronous
    public void simulate(Long gameId) {
        Game game = gameFacade.find(gameId, LockModeType.PESSIMISTIC_WRITE);
        Date today = game.getToday();
        if (today == null) {
            today = game.getStartAt();
            game.setToday(today);
        }
        Calendar calendar = calendarUtil.getCalendar(today);
        calendar.add(Calendar.DATE, 1);
        Date newToday = calendar.getTime();
        game.setToday(newToday);

        calendar = calendarUtil.getCalendar(newToday);

        List<GamePlayComponent> components = new ArrayList<>();
        components.add(GamePlayComponent.DATE);

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            simulateSunday(game);
        } else {
            simulateWeekday(game);
            components.add(GamePlayComponent.STOCKS);
        }
        if (calendar.getFirstDayOfWeek() == calendar.get(Calendar.DAY_OF_WEEK)) {
            simulateWeek(game);
            components.add(GamePlayComponent.PURCHASE);
            components.add(GamePlayComponent.DISPOSAL);
        }
        if (newToday.equals(calendarUtil.getFirstDayOfMonth(newToday))) {
            simulateMonth(game);
            components.add(GamePlayComponent.BALANCE);
        }
        if (newToday.equals(calendarUtil.getFirstDayOfYear(newToday))) {
            simulateYear(game);
        }

        Date stopAt = game.getStopAt();
        if (stopAt.equals(newToday) || stopAt.before(newToday)) {
            // @TODO terminate game, check for winners
            game.setGameStatus(GameStatus.TERMINATED);
        }

        //push.fire(new WebSocketPush(game.getId(), new GamePlayComponent[]{GamePlayComponent.DATE, GamePlayComponent.BALANCE, GamePlayComponent.STOCKS, GamePlayComponent.PURCHASE}));
        componentUpdateUtil.gameUpdate(gameId, components.toArray(new GamePlayComponent[components.size()]));
    }

    private void simulateSunday(Game game) {
        logger.log(Level.INFO, "simulate sunday: {0}", game.getToday());
        // calculate warehouse charges
        calculateWarehouseCharges(game);
    }

    private void simulateWeekday(Game game) {
        logger.log(Level.INFO, "simulate weekday: {0}", game.getToday());
        // calculate warehouse charges
        calculateWarehouseCharges(game);
        // calculate and account production
        calculateAndAccountProduction(game);
        // calculate wages
        calculateWages(game);
    }

    private void simulateWeek(Game game) {
        logger.log(Level.INFO, "simulate week: {0}", game.getToday());
        // calculate demand / supply
        calculateAndAccountSupplies(game);
        calculateAndAccountDemands(game);
    }

    private void simulateMonth(Game game) {
        logger.log(Level.INFO, "simulate month: {0}", game.getToday());
        // calculate and account overhead
        calculateAndAccountOverhead(game);
        // account warehouse charges
        accountWarehouseCharges(game);
        // account wages
        accountWages(game);

    }

    private void simulateYear(Game game) {
        logger.log(Level.INFO, "simulate year: {0}", game.getToday());
    }

    private void calculateAndAccountOverhead(Game game) throws NullPointerException {
        Set<Company> companies = game.getEconomy().getCompanies();
        Date current = game.getToday();
        companies.stream().forEach((Company company) -> {
            overheadCalculator.calculate(company, current);
        });
    }

    private void calculateAndAccountProduction(Game game) throws NullPointerException {
        Set<Company> companies = game.getEconomy().getCompanies();
        Date current = game.getToday();
        companies.stream().forEach((Company company) -> {
            productionCalculator.calculate(company, current);
        });
    }

    private void calculateWages(Game game) throws NullPointerException {
        Set<Company> companies = game.getEconomy().getCompanies();
        Date current = game.getToday();
        companies.stream().forEach((Company company) -> {
            wageCalculator.calculate(company, current);
        });
    }

    private void accountWages(Game game) throws NullPointerException {
        Set<Company> companies = game.getEconomy().getCompanies();
        Date current = game.getToday();
        companies.stream().forEach((Company company) -> {
            wageCalculator.account(company, current);
        });
    }

    private void calculateWarehouseCharges(Game game) throws NullPointerException {
        Set<Company> companies = game.getEconomy().getCompanies();
        Date current = game.getToday();
        companies.stream().forEach((Company company) -> {
            warehouseChargesCalculator.calculate(company, current);
        });
    }

    private void accountWarehouseCharges(Game game) throws NullPointerException {
        Set<Company> companies = game.getEconomy().getCompanies();
        Date current = game.getToday();
        companies.stream().forEach((Company company) -> {
            warehouseChargesCalculator.account(company, current);
        });
    }

    private void calculateAndAccountSupplies(Game game) throws NullPointerException {
        Date current = game.getToday();
        supplyCalculator.calculate(game.getEconomy(), current);
    }

    private void calculateAndAccountDemands(Game game) throws NullPointerException {
        Date current = game.getToday();
        demandCalculator.calculate(game.getEconomy(), current);
    }
}
