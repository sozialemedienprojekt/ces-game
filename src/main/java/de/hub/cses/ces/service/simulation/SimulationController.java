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
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.jsf.config.GamePlayComponent;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.util.ComponentUpdateUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class SimulationController {

    @EJB
    private GameFacade gameFacade;
    @Inject
    private ComponentUpdateUtil componentUpdateUtil;

    /**
     *
     */
    public SimulationController() {

    }

    /**
     *
     * @param gameId
     */
    public void pauseGame(Long gameId) {
        setGameStatus(gameId, GameStatus.PAUSED);
    }

    /**
     *
     * @param gameId
     */
    public void continueGame(Long gameId) {
        setGameStatus(gameId, GameStatus.RUNNING);
    }

    /**
     *
     * @param gameId
     */
    public void terminateGame(Long gameId) {
        setGameStatus(gameId, GameStatus.TERMINATED);
    }

    /**
     *
     * @param gameId
     */
    public void abortGame(Long gameId) {
        setGameStatus(gameId, GameStatus.ABORTED);
    }

    private void setGameStatus(Long gameId, GameStatus gameStatus) {
        Game game = gameFacade.find(gameId, LockModeType.PESSIMISTIC_WRITE);
        game.setGameStatus(gameStatus);
        componentUpdateUtil.gameUpdate(game.getId(), GamePlayComponent.BLOCKUI);

    }
}
