package de.hub.cses.ces.jsf.config;

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
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * contains all game play components and its ids in jsf that are be used in
 * play.xhtml these ids can be used to force an update of the corresponding game
 * play components using the ComponentUpdateUtil
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("GamePlayComponent")
@Dependent
public enum GamePlayComponent {

    BLOCKUI("blockui"),
    /**
     *
     */
    DISPOSAL("disposal"),
    /**
     *
     */
    PRODUCTION("production"),
    /**
     *
     */
    PURCHASE("purchase"),
    /**
     *
     */
    BALANCE("balance"),
    /**
     *
     */
    NOTIFICATION("notification"),
    /**
     *
     */
    DATE("date"),
    /**
     *
     */
    STOCKS("stocks"),
    /**
     *
     */
    FORM("form"),
    /**
     *
     */
    PAGE("page");

    private final String value;

    GamePlayComponent(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public final String getValue() {
        return value;
    }
}
