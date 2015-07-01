package de.hub.cses.ces.entity.listener;

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

import de.hub.cses.ces.entity.BaseEntity;
import java.util.logging.Logger;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
public class EntityListener {

    private static final Logger logger = Logger.getLogger(EntityListener.class.getName());

    /**
     *
     * @param e
     */
    @PostLoad
    public void onPostLoad(BaseEntity e) {

    }

    /**
     *
     * @param e
     */
    @PrePersist
    public void onPrePersist(BaseEntity e) {

    }

    /**
     *
     * @param e
     */
    @PostPersist
    public void onPostPersist(BaseEntity e) {

    }

    /**
     *
     * @param e
     */
    @PreUpdate
    public void onPreUpdate(BaseEntity e) {

    }

    /**
     *
     * @param e
     */
    @PostUpdate
    public void onPostUpdate(BaseEntity e) {

    }

    /**
     *
     * @param e
     */
    @PreRemove
    public void onPreRemove(BaseEntity e) {

    }

    /**
     *
     * @param e
     */
    @PostRemove
    public void onPostRemove(BaseEntity e) {

    }

}
