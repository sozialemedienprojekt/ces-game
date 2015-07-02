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
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameTiming;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Singleton
@Startup
public class SimulationScheduler {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private GameFacade gameFacade;

    @EJB
    private Simulator simulator;

    /**
     *
     */
    public SimulationScheduler() {
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "simulation scheduler initialized on startup");
    }

    /**
     *
     */
    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
    public void execute10() {
        List<Game> games = gameFacade.findAllRunningByTiming(GameTiming.TEN_SECONDS);
        games.stream().filter((Game game) -> (game != null)).forEach((Game game) -> {
            simulator.simulate(game.getId());
        });
    }

    /**
     *
     */
    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void execute5() {
        List<Game> games = gameFacade.findAllRunningByTiming(GameTiming.FIVE_SECONDS);
        games.stream().filter((Game game) -> (game != null)).forEach((Game game) -> {
            simulator.simulate(game.getId());
        });
    }
}
