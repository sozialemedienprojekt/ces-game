package de.hub.cses.ces.entity.text;

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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sgro
 */
public enum SupportedLanguage {

    /**
     *
     */
    DEUTSCH("de"),

    /**
     *
     */
    ENGLISH("en");

    private final String locale;

    SupportedLanguage(String locale) {
        this.locale = locale;
    }

    private static final Map<String, SupportedLanguage> lookup = new HashMap<>();

    static {
        // create reverse lookup hash map
        for (SupportedLanguage d : SupportedLanguage.values()) {
            lookup.put(d.getLocale(), d);
        }
    }

    /**
     *
     * @return
     */
    public String getLocale() {
        return locale;
    }

    /**
     *
     * @param locale
     * @return
     * @throws IllegalArgumentException
     */
    public static SupportedLanguage fromLocale(String locale) throws IllegalArgumentException {
        return lookup.get(locale);
    }
}
