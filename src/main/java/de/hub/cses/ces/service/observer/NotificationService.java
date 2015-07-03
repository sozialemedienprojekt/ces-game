package de.hub.cses.ces.service.observer;

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
import de.hub.cses.ces.entity.text.SupportedLanguage;
import de.hub.cses.ces.event.Notification;
import de.hub.cses.ces.event.NotificationType;
import de.hub.cses.ces.jsf.config.GamePlayComponent;
import de.hub.cses.ces.util.ComponentUpdateUtil;
import de.hub.cses.ces.util.I18nTextUtil;
import de.hub.cses.ces.util.collection.ThreadSafeSizeLimitedCollection;
import de.hub.cses.ces.util.qualifier.Notify;
import de.hub.cses.ces.util.qualifier.Scope;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@ApplicationScoped
public class NotificationService implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    private static final int MAX_ENTRIES = 20;

    @Inject
    private ComponentUpdateUtil componentUpdateUtil;

    private final Map<Long, ThreadSafeSizeLimitedCollection<Notification>> gameNotifications = new HashMap<>();
    private final Map<Long, ThreadSafeSizeLimitedCollection<Notification>> companyNotifications = new HashMap<>();
    private final Map<Long, ThreadSafeSizeLimitedCollection<Notification>> cooperatorNotifications = new HashMap<>();
    private final Map<Long, ThreadSafeSizeLimitedCollection<Notification>> clientNotifications = new HashMap<>();

    private final ReadWriteLock gameNotificationLock = new ReentrantReadWriteLock();
    private final ReadWriteLock companyNotificationLock = new ReentrantReadWriteLock();
    private final ReadWriteLock cooperatorNotificationLock = new ReentrantReadWriteLock();
    private final ReadWriteLock clientNotificationLock = new ReentrantReadWriteLock();

    /**
     *
     */
    public NotificationService() {

    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "notification observer initialized");
    }

    /**
     *
     * @param notification
     */
    public void addGameNotification(@Observes @Notify(Scope.GAME) Notification notification) {
        logger.log(Level.INFO, "notify game");
        Long gameId = notification.getId();
        if (gameId != null) {
            Lock writeLock = gameNotificationLock.writeLock();
            try {
                writeLock.lock();
                ThreadSafeSizeLimitedCollection<Notification> c;
                if ((c = this.gameNotifications.get(gameId)) == null) {
                    c = new ThreadSafeSizeLimitedCollection<>(MAX_ENTRIES);
                    this.gameNotifications.put(gameId, c);
                }
                c.add(notification);
                componentUpdateUtil.gameUpdate(gameId, GamePlayComponent.NOTIFICATION);
                //push.fire(new WebSocketPush(notification.getId(), ClientAction.UPDATE, new GamePlayComponent[]{GamePlayComponent.NOTIFICATION}));
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     *
     * @param notification
     */
    public void addCompanyNotification(@Observes @Notify(Scope.COMPANY) Notification notification) {
        logger.log(Level.INFO, "notify company");
        Long companyId = notification.getId();
        if (companyId != null) {
            Lock writeLock = companyNotificationLock.writeLock();
            try {
                writeLock.lock();
                ThreadSafeSizeLimitedCollection<Notification> c;
                if ((c = this.companyNotifications.get(companyId)) == null) {
                    c = new ThreadSafeSizeLimitedCollection<>(MAX_ENTRIES);
                    this.companyNotifications.put(companyId, c);
                }
                c.add(notification);
                componentUpdateUtil.companyUpdate(companyId, GamePlayComponent.NOTIFICATION);
                //push.fire(new WebSocketPush(notification.getId(), ClientAction.UPDATE, new GamePlayComponent[]{GamePlayComponent.NOTIFICATION}));
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     *
     * @param notification
     */
    public void addCooperatorNotification(@Observes @Notify(Scope.COOPERATOR) Notification notification) {
        logger.log(Level.INFO, "notify cooperator");
        Long cooperatorId = notification.getId();
        if (cooperatorId != null) {
            Lock writeLock = cooperatorNotificationLock.writeLock();
            try {
                writeLock.lock();
                ThreadSafeSizeLimitedCollection<Notification> c;
                if ((c = this.cooperatorNotifications.get(cooperatorId)) == null) {
                    c = new ThreadSafeSizeLimitedCollection<>(MAX_ENTRIES);
                    this.cooperatorNotifications.put(cooperatorId, c);
                }
                c.add(notification);
                componentUpdateUtil.cooperatorUpdate(cooperatorId, GamePlayComponent.NOTIFICATION);
                //push.fire(new WebSocketPush(notification.getId(), ClientAction.UPDATE, new GamePlayComponent[]{GamePlayComponent.NOTIFICATION}));
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     *
     * @param notification
     */
    public void addClientNotification(@Observes @Notify(Scope.CLIENT) Notification notification) {
        logger.log(Level.INFO, "notify client");
        Long clientId = notification.getId();
        if (clientId != null) {
            Lock writeLock = clientNotificationLock.writeLock();
            try {
                writeLock.lock();
                ThreadSafeSizeLimitedCollection<Notification> c;
                if ((c = this.clientNotifications.get(clientId)) == null) {
                    c = new ThreadSafeSizeLimitedCollection<>(MAX_ENTRIES);
                    this.clientNotifications.put(clientId, c);
                }
                c.add(notification);
                componentUpdateUtil.clientUpdate(clientId, GamePlayComponent.NOTIFICATION);
                //push.fire(new WebSocketPush(notification.getId(), ClientAction.UPDATE, new GamePlayComponent[]{GamePlayComponent.NOTIFICATION}));
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     *
     * @param notification
     */
    public void addGloablNotification(@Observes @Notify(Scope.GLOBAL) Notification notification) {
        logger.log(Level.INFO, "notify");

        Lock writeLock = cooperatorNotificationLock.writeLock();

        try {
            writeLock.lock();
            cooperatorNotifications.values().stream().forEach((notifications) -> {
                notifications.add(notification);
                componentUpdateUtil.globalUpdate(GamePlayComponent.NOTIFICATION);
//                push.fire(new WebSocketPush(notification.getId(), ClientAction.UPDATE, new GamePlayComponent[]{GamePlayComponent.NOTIFICATION}));
            });
        } finally {
            writeLock.unlock();
        }
    }

    /**
     *
     * @param gameId
     * @param companyId
     * @param cooperatorId
     * @param clientId
     * @return
     */
    public List<Notification> getAll(Long gameId, Long companyId, Long cooperatorId, Long clientId) {
        List<Notification> notificationList = new ArrayList<>();
        if (gameId != null) {
            notificationList.addAll(getGameNotifications(gameId));
        }
        if (companyId != null) {
            notificationList.addAll(getCompanyNotifications(companyId));
        }
        if (cooperatorId != null) {
            notificationList.addAll(getCooperatorNotifications(cooperatorId));
        }
        if (clientId != null) {
            notificationList.addAll(getClientNotifications(clientId));
        }

        return notificationList;
    }

    /**
     *
     * @param gameId
     * @return
     */
    public List<Notification> getGameNotifications(Long gameId) {
        List<Notification> notificationList = new ArrayList<>();
        Lock readLock = gameNotificationLock.readLock();
        try {
            readLock.lock();
            Collection<Notification> c;
            if ((c = gameNotifications.get(gameId)) != null) {
                notificationList.addAll(c);

            }
        } finally {
            readLock.unlock();
        }
        return notificationList;
    }

    /**
     *
     * @param companyId
     * @return
     */
    public List<Notification> getCompanyNotifications(Long companyId) {
        List<Notification> notificationList = new ArrayList<>();
        Lock readLock = companyNotificationLock.readLock();
        try {
            readLock.lock();
            Collection<Notification> c;
            if ((c = companyNotifications.get(companyId)) != null) {
                notificationList.addAll(c);

            }
        } finally {
            readLock.unlock();
        }
        return notificationList;
    }

    /**
     *
     * @param cooperatorId
     * @return
     */
    public List<Notification> getCooperatorNotifications(Long cooperatorId) {
        List<Notification> notificationList = new ArrayList<>();
        Lock readLock = cooperatorNotificationLock.readLock();
        try {
            readLock.lock();
            Collection<Notification> c;
            if ((c = cooperatorNotifications.get(cooperatorId)) != null) {
                notificationList.addAll(c);

            }
        } finally {
            readLock.unlock();
        }
        return notificationList;
    }

    /**
     *
     * @param clientId
     * @return
     */
    public List<Notification> getClientNotifications(Long clientId) {
        List<Notification> notificationList = new ArrayList<>();
        Lock readLock = clientNotificationLock.readLock();
        try {
            readLock.lock();
            Collection<Notification> c;
            if ((c = clientNotifications.get(clientId)) != null) {
                notificationList.addAll(c);

            }
        } finally {
            readLock.unlock();
        }
        return notificationList;
    }

}
