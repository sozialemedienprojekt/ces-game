package de.hub.cses.ces.service.persistence.game;

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
import de.hub.cses.ces.entity.game.GameTiming;
import de.hub.cses.ces.service.persistence.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class GameFacade extends AbstractFacade<Game> {

    @PersistenceContext(unitName = "de.hub.cses.ces.PersistenceUnit")
    private EntityManager em;

    /**
     *
     */
    public GameFacade() {
        super(Game.class);
    }

    /**
     *
     * @return
     */
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @param gameTiming
     * @return
     */
    public List<Game> findAllRunningByTiming(GameTiming gameTiming) {
        if (gameTiming == null) {
            return null;
        }
        TypedQuery<Game> query = em.createNamedQuery("Game.findRunningByGameTiming", Game.class);
        query.setParameter("gameTiming", gameTiming);
        query.setParameter("gameStatus", GameStatus.RUNNING);
        return query.getResultList();
    }

    /**
     *
     * @param gameStatus
     * @return
     */
    public List<Game> findAllByStatus(GameStatus gameStatus) {
        if (gameStatus == null) {
            return null;
        }
        TypedQuery<Game> query = em.createNamedQuery("Game.findByGameStatus", Game.class);
        query.setParameter("gameStatus", gameStatus);
        return query.getResultList();
    }

    /**
     *
     * @param identifier
     * @return
     */
    public boolean exists(String identifier) {
        if (identifier == null) {
            return false;
        }
        TypedQuery<Game> query = em.createNamedQuery("Game.findByIdentifer", Game.class);
        query.setParameter("identifier", identifier);
        return (query.getResultList().size() > 0);
    }
}
