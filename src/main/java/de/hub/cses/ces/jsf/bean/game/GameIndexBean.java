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

import de.hub.cses.ces.entity.client.User;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.util.qualifier.Identify;
import java.io.Serializable;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author sgro
 */
@Named("GameIndexBean")
@ViewScoped
public class GameIndexBean implements Serializable {

    @Inject
    @Identify
    private User user;

    @EJB
    private GameFacade gameFacade;

    /**
     *
     */
    public GameIndexBean() {

    }

    /**
     *
     * @return
     */
    public Collection<Game> getGames() {
        return gameFacade.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean isCooperator(Long id) {
        if (user == null || id == null) {
            return false;
        }
        return (id.equals(user.getId()));
    }

}
