package de.hub.cses.ces.util;

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
import de.hub.cses.ces.event.WebSocketPush;
import de.hub.cses.ces.jsf.config.GamePlayComponent;
import de.hub.cses.ces.util.qualifier.Push;
import de.hub.cses.ces.util.qualifier.Scope;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * provides methods to force an update of game play components
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@RequestScoped
public class ComponentUpdateUtil {

    @Inject
    @Push(Scope.GLOBAL)
    private Event<WebSocketPush> globalPush;

    @Inject
    @Push(Scope.GAME)
    private Event<WebSocketPush> gamePush;

    @Inject
    @Push(Scope.COMPANY)
    private Event<WebSocketPush> companyPush;

    @Inject
    @Push(Scope.COOPERATOR)
    private Event<WebSocketPush> cooperatorPush;

    @Inject
    @Push(Scope.CLIENT)
    private Event<WebSocketPush> clientPush;

    /**
     *
     * @param gameComponents the game play components to be updated
     */
    public void globalUpdate(GamePlayComponent... gameComponents) {
        WebSocketPush event = new WebSocketPush(null, gameComponents);
        globalPush.fire(event);
    }

    /**
     *
     * @param gameId id of the game where game play components will be updated
     * @param gameComponents the game play components to be updated
     */
    public void gameUpdate(Long gameId, GamePlayComponent... gameComponents) {
        WebSocketPush event = new WebSocketPush(gameId, gameComponents);
        gamePush.fire(event);
    }

    /**
     *
     * @param companyId id of the company where game play components will be
     * updated
     * @param gameComponents the game play components to be updated
     */
    public void companyUpdate(Long companyId, GamePlayComponent... gameComponents) {
        WebSocketPush event = new WebSocketPush(companyId, gameComponents);
        companyPush.fire(event);
    }

    /**
     *
     * @param cooperatorId id of the cooperator where game play components will
     * be updated
     * @param gameComponents the game play components to be updated
     */
    public void cooperatorUpdate(Long cooperatorId, GamePlayComponent... gameComponents) {
        WebSocketPush event = new WebSocketPush(cooperatorId, gameComponents);
        cooperatorPush.fire(event);
    }

    /**
     *
     * @param clientId id of the client where game play components will be
     * updated
     * @param gameComponents the game play components to be updated
     */
    public void clientUpdate(Long clientId, GamePlayComponent... gameComponents) {
        WebSocketPush event = new WebSocketPush(clientId, gameComponents);
        clientPush.fire(event);
    }

}
