package de.hub.cses.ces.jsf.bean.game;

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
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.service.simulation.SimulationController;
import de.hub.cses.ces.service.simulation.SimulationInitializer;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("ControlBean")
@ViewScoped
public class ControlBean implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private GameFacade gameFacade;

    @EJB
    private SimulationController simulationController;

    @EJB
    private SimulationInitializer simulationInitializer;

    private Game selectedGame;

    /**
     *
     * @return
     */
    public Collection<Game> getGames() {
        return gameFacade.findAll();
    }

    /**
     *
     * @param gameId
     */
    public void setGameId(Long gameId) {
        if (gameId != null) {
            selectedGame = gameFacade.find(gameId);
        }
    }

    /**
     *
     * @return
     */
    public Long getGameId() {
        return (selectedGame != null) ? selectedGame.getId() : null;
    }

    /**
     *
     * @return
     */
    public Game getSelectedGame() {
        if (selectedGame == null) {
            return null;
        }
        return gameFacade.find(selectedGame.getId());
    }

    /**
     *
     * @param selectedGame
     */
    public void setSelectedGame(Game selectedGame) {
        this.selectedGame = selectedGame;
    }

    /**
     *
     */
    public void startGame() {
        if (selectedGame == null) {
            logger.log(Level.INFO, "game is null");
            return;
        }
        simulationInitializer.initialize(selectedGame.getId());
    }

    /**
     *
     */
    public void pauseGame() {
        if (selectedGame == null) {
            logger.log(Level.INFO, "game is null");
            return;
        }
        simulationController.pauseGame(selectedGame.getId());
    }

    /**
     *
     */
    public void continueGame() {
        if (selectedGame == null) {
            logger.log(Level.INFO, "game is null");
            return;
        }
        simulationController.continueGame(selectedGame.getId());
    }

    /**
     *
     */
    public void terminateGame() {
        if (selectedGame == null) {
            logger.log(Level.INFO, "game is null");
            return;
        }
        simulationController.terminateGame(selectedGame.getId());
    }

}
