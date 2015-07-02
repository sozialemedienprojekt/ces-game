package de.hub.cses.ces.jsf.bean.util;

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
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 * @param <T>
 */
public abstract class IdentityUtil<T extends BaseEntity> {

    private final Class<T> clazz;

    /**
     *
     * @param clazz
     */
    protected IdentityUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     *
     * @param ip
     * @return
     */
    public abstract T identify(InjectionPoint ip);

    /**
     *
     * @param ip
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public T instantiate(InjectionPoint ip) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}
