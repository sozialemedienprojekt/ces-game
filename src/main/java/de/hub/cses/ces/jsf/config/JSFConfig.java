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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("JSFConfig")
@RequestScoped
public class JSFConfig {

    private static final GamePlayComponent BLOCKUI = GamePlayComponent.BLOCKUI;
    private static final GamePlayComponent DISPOSAL = GamePlayComponent.DISPOSAL;
    private static final GamePlayComponent PRODUCTION = GamePlayComponent.PRODUCTION;
    private static final GamePlayComponent PURCHASE = GamePlayComponent.PURCHASE;
    private static final GamePlayComponent STOCKS = GamePlayComponent.STOCKS;
    private static final GamePlayComponent BALANCE = GamePlayComponent.BALANCE;
    private static final GamePlayComponent NOTIFICATION = GamePlayComponent.NOTIFICATION;
    private static final GamePlayComponent DATE = GamePlayComponent.DATE;
    private static final GamePlayComponent FORM = GamePlayComponent.FORM;
    private static final GamePlayComponent PAGE = GamePlayComponent.PAGE;

    private static final String WS_PATH = "/websocket/server";
    private static final String WS_PROTOCOL = "wss";

    @Inject
    private ServletContext context;

    /**
     *
     * @return the context path that will be used by the JavaScript websocket
     * client
     */
    public String getContextPath() {
        return context.getContextPath();
    }

    /**
     *
     * @return the websocket path that will be used by the JavaScript websocket
     * client
     */
    public String getWebSocketPath() {
        return WS_PATH;
    }

    /**
     *
     * @return the websocket protocol (wss) that will be used by the JavaScript
     * websocket client
     */
    public String getWebSocketProtocol() {
        return WS_PROTOCOL;
    }

    /**
     *
     * @return the id of the notification component (see play.xhtml)
     */
    public String getNotificationComponentId() {
        return NOTIFICATION.getValue();
    }

    /**
     *
     * @return the id of the date component (see play.xhtml)
     */
    public String getDateComponentId() {
        return DATE.getValue();
    }

    /**
     *
     * @return the id of the balance component (see play.xhtml)
     */
    public String getBalanceComponentId() {
        return BALANCE.getValue();
    }

    /**
     *
     * @return the id of the stocks component (see play.xhtml)
     */
    public String getStocksComponentId() {
        return STOCKS.getValue();
    }

    /**
     *
     * @return the id of the purchase component (see play.xhtml)
     */
    public String getPurchaseComponentId() {
        return PURCHASE.getValue();
    }

    /**
     *
     * @return the id of the production component (see play.xhtml)
     */
    public String getProductionComponentId() {
        return PRODUCTION.getValue();
    }

    /**
     *
     * @return the id of the disposal component (see play.xhtml)
     */
    public String getDisposalComponentId() {
        return DISPOSAL.getValue();
    }

    /**
     *
     * @return the id of the block ui component (see play.xhtml)
     */
    public String getBlockUIComponentId() {
        return BLOCKUI.getValue();
    }
}
