package de.hub.cses.ces.jsf.converter;

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
import java.util.Collection;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 * @param <T>
 */
public abstract class AbstractEntityConverter<T extends BaseEntity> implements Converter {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    private final Class<T> clazz;

    /**
     *
     * @param clazz
     */
    protected AbstractEntityConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     *
     * @return
     */
    public abstract Collection<T> getEntities();

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        //logger.log(Level.INFO, "get as object: {0}", value);
        T t = null;
        int id = Integer.valueOf(value);
        if (value != null && id > 0) {
            for (T _t : getEntities()) {
                if (_t.getId() == id) {
                    t = _t;
                }
            }
        }
        return t;
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        //logger.log(Level.INFO, "get as string: {0}", value);
        String returnVal;
        if (clazz != null && value != null && clazz.isAssignableFrom(value.getClass())) {
            T t = (T) value;
            returnVal = String.valueOf(t.getId());
        } else {
            returnVal = "";
        }
        return returnVal;
    }
}
