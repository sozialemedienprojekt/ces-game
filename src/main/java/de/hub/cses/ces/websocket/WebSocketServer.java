package de.hub.cses.ces.websocket;

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
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.event.WebSocketPush;
import de.hub.cses.ces.jsf.config.GamePlayComponent;
import de.hub.cses.ces.service.persistence.company.cooperator.CooperatorFacade;
import de.hub.cses.ces.util.qualifier.Push;
import de.hub.cses.ces.util.qualifier.Scope;
import de.hub.cses.ces.websocket.message.ComponentUpdate;
import de.hub.cses.ces.websocket.message.MessageDecoder;
import de.hub.cses.ces.websocket.message.MessageEncoder;
import de.hub.cses.ces.websocket.message.WebSocketMessage;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * creates a singleton websocket server instance
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Singleton
@ServerEndpoint(value = "/websocket/server/{cooperatorId}",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class})
public class WebSocketServer {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private CooperatorFacade cooperatorFacade;

    private static final boolean DEBUG = false;

    private final Map<Long, Set<Session>> gameSessions = new HashMap<>();
    private final Map<Long, Set<Session>> companySessions = new HashMap<>();
    private final Map<Long, Set<Session>> cooperatorSessions = new HashMap<>();
    private final Map<Long, Set<Session>> userSessions = new HashMap<>();

    /**
     *
     * @param message incoming message of websocket client
     * @param session session of websocket client
     */
    @OnMessage
    @Lock(LockType.WRITE)
    public void onMessage(WebSocketMessage message, Session session) {
        logger.log(Level.INFO, "message: {0}", message.toString());
    }

    /**
     *
     * @param session session of websocket client
     * @param cooperatorId id of the cooperator
     */
    @OnOpen
    @Lock(LockType.WRITE)
    public void onOpen(Session session, @PathParam("cooperatorId") String cooperatorId) {
        try {
            if (cooperatorId == null) {
                session.close();
                return;
            }
            Long _cooperatorId = Long.parseLong(cooperatorId);

            Cooperator cooperator = cooperatorFacade.find(_cooperatorId, LockModeType.OPTIMISTIC);

            Principal principal = session.getUserPrincipal();
            String clientname = principal.getName();

            if (cooperator == null || !cooperator.getUser().getClientname().equals(clientname)) {
                session.close();
                return;
            }

            Company company = cooperator.getCompany();
            Economy economy = company.getEconomy();
            Game game = economy.getGame();

            register(session, game, company, cooperator);
        } catch (NullPointerException | IOException | NumberFormatException ex) {
            logger.log(Level.WARNING, null, ex);
            try {
                session.close();
            } catch (IOException ex1) {
            }
        }
    }

    /**
     *
     * @param session session of the websocket client
     */
    @Lock(LockType.WRITE)
    @OnClose
    public void onClose(Session session) {
        remove(session);
    }

    /**
     *
     * @param event
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void gamePush(@Observes(during = TransactionPhase.AFTER_SUCCESS) @Push(Scope.GAME) WebSocketPush event) {
        logger.log(Level.INFO, "game push");
        gamePush(event.getId(), createComponentUpdate(event.getComponents()));
    }

    /**
     *
     * @param gameId id of the game to which the websocket message will be
     * pushed
     * @param message the message to be pushed
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void gamePush(Long gameId, WebSocketMessage message) {
        try {
            Set<Session> sessions = gameSessions.get(gameId);
            push(sessions, message);
        } catch (NullPointerException npe) {

        }
    }

    /**
     *
     * @param event cdi event
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void globalPush(@Observes(during = TransactionPhase.AFTER_SUCCESS) @Push(Scope.GLOBAL) WebSocketPush event) {
        logger.log(Level.INFO, "global push");
        globalPush(createComponentUpdate(event.getComponents()));
    }

    /**
     *
     * @param message the message to be pushed to all connected websocket
     * clients
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void globalPush(WebSocketMessage message) {
        try {
            Set<Session> sessions = new HashSet<>();
            userSessions.values().stream().forEach((userSessionSet) -> {
                sessions.addAll(userSessionSet);
            });
            push(sessions, message);
        } catch (NullPointerException npe) {

        }
    }

    /**
     *
     * @param event cdi event
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void userPush(@Observes(during = TransactionPhase.AFTER_SUCCESS) @Push(Scope.CLIENT) WebSocketPush event) {
        logger.log(Level.INFO, "user push");
        userPush(event.getId(), createComponentUpdate(event.getComponents()));
    }

    /**
     *
     * @param userId id of the user to which the websocket message will be
     * pushed
     * @param message the message to be pushed
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void userPush(Long userId, WebSocketMessage message) {
        try {
            Set<Session> sessions = userSessions.get(userId);
            push(sessions, message);
        } catch (NullPointerException npe) {

        }
    }

    /**
     *
     * @param event cdi event
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void cooperatorPush(@Observes(during = TransactionPhase.AFTER_SUCCESS) @Push(Scope.COOPERATOR) WebSocketPush event) {
        logger.log(Level.INFO, "cooperator push");
        cooperatorPush(event.getId(), createComponentUpdate(event.getComponents()));
    }

    /**
     *
     * @param cooperatorId id of the cooperator to which the websocket message
     * will be pushed
     * @param message the message to be pushed
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void cooperatorPush(Long cooperatorId, WebSocketMessage message) {
        try {
            Set<Session> sessions = cooperatorSessions.get(cooperatorId);
            push(sessions, message);
        } catch (NullPointerException npe) {

        }
    }

    /**
     *
     * @param event cdi event
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void companyPush(@Observes(during = TransactionPhase.AFTER_SUCCESS) @Push(Scope.COMPANY) WebSocketPush event) {
        logger.log(Level.INFO, "company push");
        companyPush(event.getId(), createComponentUpdate(event.getComponents()));
    }

    /**
     *
     * @param companyId id of the company to which the websocket message will be
     * pushed
     * @param message the message to be pushed
     */
    @Asynchronous
    @Lock(LockType.WRITE)
    public void companyPush(Long companyId, WebSocketMessage message) {
        try {
            Set<Session> sessions = companySessions.get(companyId);
            push(sessions, message);
        } catch (NullPointerException npe) {

        }
    }

    /**
     *
     * @param sessions set of sessions to which the websocket message will be
     * pushed
     * @param message the message to be pushed
     */
    private void push(Set<Session> sessions, WebSocketMessage message) {
        if (sessions != null && !sessions.isEmpty()) {
            Set<Session> expiredSessions = new HashSet<>();
            sessions.stream().forEach((session) -> {
                try {
                    push(session, message);
                } catch (EncodeException ee) {

                } catch (IllegalStateException | IOException | NullPointerException ex) {
                    expiredSessions.add(session);
                }
            });
            if (!expiredSessions.isEmpty()) {
                expiredSessions.stream().forEach((Session session) -> {
                    remove(session);
                });
            }
        }
    }

    /**
     *
     * @param session session of the websocket client to which the websocket
     * message will be pushed
     * @param message the message to be pushed
     * @throws IOException
     * @throws EncodeException
     */
    private void push(Session session, WebSocketMessage message) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(message);
    }

    private void register(Session session, Game game, Company company, Cooperator cooperator) throws NullPointerException {
        Long gameId = game.getId();
        Set<Session> gameSessionSet;
        if (gameSessions.containsKey(gameId)) {
            gameSessionSet = gameSessions.get(gameId);
        } else {
            gameSessionSet = new HashSet<>();
            gameSessions.put(gameId, gameSessionSet);
        }
        gameSessionSet.add(session);

        Long companyId = company.getId();
        Set<Session> companySessionSet;
        if (companySessions.containsKey(companyId)) {
            companySessionSet = companySessions.get(companyId);
        } else {
            companySessionSet = new HashSet<>();
            companySessions.put(companyId, companySessionSet);
        }
        companySessionSet.add(session);
        Long cooperatorId = cooperator.getId();
        Set<Session> cooperatorSessionSet;
        if (cooperatorSessions.containsKey(cooperatorId)) {
            cooperatorSessionSet = cooperatorSessions.get(cooperatorId);
        } else {
            cooperatorSessionSet = new HashSet<>();
            cooperatorSessions.put(cooperatorId, cooperatorSessionSet);
        }
        cooperatorSessionSet.add(session);
        Long clientId = cooperator.getUser().getId();
        Set<Session> userSessionSet;
        if (userSessions.containsKey(clientId)) {
            userSessionSet = userSessions.get(clientId);
        } else {
            userSessionSet = new HashSet<>();
            userSessions.put(clientId, userSessionSet);
        }
        userSessionSet.add(session);
    }

    /**
     * removes the corresponding session from all sets
     *
     * @param session session to be removed
     */
    private void remove(Session session) {
        gameSessions.values().stream().forEach((gameSessionSet) -> {
            gameSessionSet.remove(session);
        });
        companySessions.values().stream().forEach((companySessionSet) -> {
            companySessionSet.remove(session);
        });
        cooperatorSessions.values().stream().forEach((cooperatorSessionSet) -> {
            cooperatorSessionSet.remove(session);
        });
        userSessions.values().stream().forEach((userSessionSet) -> {
            userSessionSet.remove(session);
        });
    }

    /**
     * creates a message that forces an update game play components
     *
     * @param components components to be updated
     * @return created websocket message
     */
    private WebSocketMessage createComponentUpdate(GamePlayComponent[] components) {
        List<String> componentList = null;
        if (components != null) {
            componentList = new ArrayList<>();
            for (GamePlayComponent component : components) {
                componentList.add(component.getValue());
            }
        }
        WebSocketMessage message = new ComponentUpdate(componentList);
        return message;
    }

}
