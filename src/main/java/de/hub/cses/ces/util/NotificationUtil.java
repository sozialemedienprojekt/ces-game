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
import de.hub.cses.ces.entity.text.I18nText;
import de.hub.cses.ces.entity.text.SupportedLanguage;
import de.hub.cses.ces.event.Notification;
import de.hub.cses.ces.event.NotificationType;
import de.hub.cses.ces.util.qualifier.Notify;
import de.hub.cses.ces.util.qualifier.Scope;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * provides methods for sending notifications to clients
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@RequestScoped
public class NotificationUtil {

    @Inject
    @Notify(Scope.GLOBAL)
    private Event<Notification> globalNotification;

    @Inject
    @Notify(Scope.GAME)
    private Event<Notification> gameNotification;

    @Inject
    @Notify(Scope.COMPANY)
    private Event<Notification> companyNotification;

    @Inject
    @Notify(Scope.COOPERATOR)
    private Event<Notification> cooperatorNotification;

    @Inject
    @Notify(Scope.CLIENT)
    private Event<Notification> clientNotification;

    @Inject
    private I18nTextUtil i18nTextUtil;

    /**
     * notifies all clients
     *
     * @param message the message to be sent
     * @param type type of notification
     */
    public void notifyClients(Map<SupportedLanguage, String> message, NotificationType type) {
        I18nText text = i18nTextUtil.create(message);
        Notification notification = new Notification(null, type, text);
        globalNotification.fire(notification);
    }

    /**
     * notifies all clients of a specific game
     *
     * @param gameId id of the game to which the notification will be sent
     * @param message the message to be sent
     * @param type type of notification
     */
    public void notifyGame(Long gameId, Map<SupportedLanguage, String> message, NotificationType type) {
        I18nText text = i18nTextUtil.create(message);
        Notification notification = new Notification(gameId, type, text);
        gameNotification.fire(notification);
    }

    /**
     * notifies all clients of a specific company
     *
     * @param companyId id of the company to which the notification will be sent
     * @param message the message to be sent
     * @param type type of notification
     */
    public void notifyCompany(Long companyId, Map<SupportedLanguage, String> message, NotificationType type) {
        I18nText text = i18nTextUtil.create(message);
        Notification notification = new Notification(companyId, type, text);
        companyNotification.fire(notification);
    }

    /**
     * notifies a specific cooperator
     *
     * @param cooperatorId id of the cooperator who will be notified
     * @param message the message to be sent
     * @param type type of notification
     */
    public void notifyCooperator(Long cooperatorId, Map<SupportedLanguage, String> message, NotificationType type) {
        I18nText text = i18nTextUtil.create(message);
        Notification notification = new Notification(cooperatorId, type, text);
        cooperatorNotification.fire(notification);
    }

    /**
     * notifies a specific client
     *
     * @param clientId id of the client that will be notified
     * @param message the message to be sent
     * @param type type of notification
     */
    public void notifyClient(Long clientId, Map<SupportedLanguage, String> message, NotificationType type) {
        I18nText text = i18nTextUtil.create(message);
        Notification notification = new Notification(clientId, type, text);
        clientNotification.fire(notification);
    }

}
