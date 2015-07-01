package de.hub.cses.ces.jsf.bean.game;

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
import de.hub.cses.ces.entity.economy.EconomicData;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.economy.HistoricalEconomicData;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameTiming;
import de.hub.cses.ces.entity.market.Market;
import de.hub.cses.ces.service.persistence.economy.market.MarketFacade;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("CreationBean")
@ViewScoped
public class CreationBean implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private MarketFacade marketFacade;

    @EJB
    private GameFacade gameFacade;

    private Game game = new Game();

    private Economy economy = new Economy();

    private Collection<Market> markets;

    private Market market;

    private InputText identifier;

    /**
     *
     */
    public CreationBean() {
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        markets = marketFacade.findAll();
    }

    /**
     *
     * @return
     */
    public Collection<Market> getMarkets() {
        return markets;
    }

    /**
     *
     * @param markets
     */
    public void setMarket(Collection<Market> markets) {
        this.markets = markets;
    }

    /**
     *
     * @return
     */
    public Market getMarket() {
        return market;
    }

    /**
     *
     * @param market
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     *
     * @return
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @return
     */
    public GameTiming[] getTimings() {
        return GameTiming.values();
    }

    /**
     *
     * @return
     */
    public String createGame() {

        String value = (String) identifier.getValue();

        if (gameFacade.exists(value)) {
            logger.log(Level.INFO, "schon da");
            String errorMessage = FacesContext.getCurrentInstance().getApplication().
                    getResourceBundle(FacesContext.getCurrentInstance(), "i18n_txts").
                    getString("exists");
            // Add View Faces Message
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    errorMessage, errorMessage);
            // Add the message into context for a specific component
            FacesContext.getCurrentInstance().addMessage(identifier.getClientId(), message);
            return null;
        }

        game.setIdentifier(value);
        String password = game.getPassword();
        if (password != null && !password.isEmpty()) {
            game.setPassword(DigestUtils.sha512Hex(password));
        }
        economy.setGame(game);
        economy.setMarket(market);
        economy.setEconomicData(new EconomicData());
        economy.getEconomicData().setHistoricalEconomicData(new HistoricalEconomicData());
        game.setEconomy(economy);
        gameFacade.create(game);
        logger.log(Level.INFO, "Game {0} created.", game);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String url = ec.encodeActionURL("/game/setup.xhtml?faces-redirect=true&game-id=" + game.getId());
        return url;
    }

    /**
     *
     * @return
     */
    public InputText getIdentifier() {
        return identifier;
    }

    /**
     *
     * @param identifier
     */
    public void setIdentifier(InputText identifier) {
        this.identifier = identifier;
    }

    /**
     *
     */
    public void cancel() {
        this.game = new Game();
    }
}
