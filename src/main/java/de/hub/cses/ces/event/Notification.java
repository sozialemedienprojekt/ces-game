package de.hub.cses.ces.event;

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

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
public final class Notification extends Event implements Comparable<Notification> {

    private final NotificationType type;
    private final I18nText message;
    private final Long timestamp;

    /**
     *
     * @param id
     * @param type
     * @param message
     */
    public Notification(Long id, NotificationType type, I18nText message) {
        super(id);
        this.type = type;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public NotificationType getType() {
        return type;
    }
    

    /**
     *
     * @return
     */
    public I18nText getMessage() {
        return message;
    }

    @Override
    public int compareTo(Notification o) {
        return Long.compare(this.timestamp, o.timestamp);
    }

}
