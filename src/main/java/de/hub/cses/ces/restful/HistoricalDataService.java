package de.hub.cses.ces.restful;

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
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author sgro
 */
@Path("historical/economic/data")
@RequestScoped
public class HistoricalDataService {

    @Context
    private UriInfo context;

    @EJB
    private GameFacade gameFacade;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger logger;

    /**
     * Creates a new instance of HistoricData
     */
    public HistoricalDataService() {
    }

    /**
     * Retrieves representation of an instance of
     * de.hub.cses.ces.restful.service.HistoricalDataService
     *
     * @param gameId id of the game for which the historical supply data will be
     * returned
     * @param limit number of max entries to be returned
     * @return the historical data represented by a json structured string
     */
    @GET
    @Path("get/supplies/{game-id}/{limit}")
    @Produces("application/json")
    public String getHistoricalSupplyData(@PathParam("game-id") Long gameId, @PathParam("limit") int limit) {
        if (gameId == null) {
            throw new WebApplicationException("Parameter 'game-id' must not be null.");
        }
        Game game = gameFacade.find(gameId);
        if (game == null) {
            throw new WebApplicationException(String.format("Game with id {0} could not be found.", gameId));
        }
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            mapper.writeValue(stream, game.getEconomy().getEconomicData().getHistoricalEconomicData().getDynamicSupplies());
            return stream.toString("UTF-8");
        } catch (IOException | NullPointerException ex) {
            throw new WebApplicationException("Object could not be encoded.");
        }
    }

    /**
     *
     * @param gameId id of the game for which the historical demand data will be
     * returned
     * @param limit number of max. entries
     * @return the historical data represented by a json structured string
     */
    @GET
    @Path("get/demands/{game-id}/{limit}")
    @Produces("application/json")
    public String getHistoricalDemandData(@PathParam("game-id") Long gameId, @PathParam("limit") int limit) {
        if (gameId == null) {
            throw new WebApplicationException("Parameter 'game-id' must not be null.");
        }
        Game game = gameFacade.find(gameId);
        if (game == null) {
            throw new WebApplicationException(String.format("Game with id {0} could not be found.", gameId));
        }
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            mapper.writeValue(stream, game.getEconomy().getEconomicData().getHistoricalEconomicData().getDynamicDemands());
            return stream.toString("UTF-8");
        } catch (IOException | NullPointerException ex) {
            throw new WebApplicationException("Object could not be encoded.");
        }
    }

    /**
     *
     * @param gameId id of the game for which the historical data will be
     * returned
     * @return the historical data represented by a json structured string
     */
    @GET
    @Path("get/all/{game-id}")
    @Produces("application/json")
    public String getHistoricalData(@PathParam("game-id") Long gameId) {
        if (gameId == null) {
            throw new WebApplicationException("Parameter 'game-id' must not be null.");
        }
        Game game = gameFacade.find(gameId);
        if (game == null) {
            throw new WebApplicationException(String.format("Game with id {0} could not be found.", gameId));
        }
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            mapper.writeValue(stream, game.getEconomy().getEconomicData().getHistoricalEconomicData());
            return stream.toString("UTF-8");
        } catch (IOException | NullPointerException ex) {
            throw new WebApplicationException("Object could not be encoded.");
        }
    }
}
