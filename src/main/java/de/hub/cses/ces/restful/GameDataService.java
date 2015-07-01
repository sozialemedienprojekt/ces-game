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

import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.Part;
import de.hub.cses.ces.entity.product.Product;
import de.hub.cses.ces.entity.production.ProductionPlan;
import de.hub.cses.ces.service.persistence.company.CompanyFacade;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author sgro
 */
@Path("game/data")
@RequestScoped
public class GameDataService {

    @Context
    private UriInfo context;

    @EJB
    private GameFacade gameFacade;

    @EJB
    private CompanyFacade companyFacade;

    @GET
    @Path("get/products/{game-id}")
    @Produces("application/json")
    public String getAvailableProducts(@PathParam("game-id") Long gameId) {
        if (gameId == null) {
            throw new WebApplicationException("Parameter 'game-id' must not be null.");
        }
        Game game = gameFacade.find(gameId);
        if (game == null) {
            throw new WebApplicationException(String.format("Game with id {0} could not be found.", gameId));
        }
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_OBJECT);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            Set<Sector> sectors = game.getEconomy().getMarket().getSectors();
            Set<Product> products = new HashSet<>();
            for (Sector sector : sectors) {
                Set<FinalProduct> finalProducts = sector.getFinalProducts();
                products.addAll(finalProducts);
                for (FinalProduct finalProduct : finalProducts) {
                    Set<Part> parts = finalProduct.getPartsList().getParts();
                    for (Part part : parts) {
                        products.add(part.getIntermediateProduct());
                    }
                }
            }
            mapper.writerWithType(new TypeReference<Set<Product>>() {
            }).writeValue(stream, products);
            return stream.toString("UTF-8");
        } catch (IOException | NullPointerException ex) {
            throw new WebApplicationException("Object could not be encoded.");
        }
    }

    @GET
    @Path("get/products/producible/{company-id}")
    @Produces("application/json")
    public String getProducibleProducts(@PathParam("company-id") Long companyId) {
        if (companyId == null) {
            throw new WebApplicationException("Parameter 'game-id' must not be null.");
        }
        Company company = companyFacade.find(companyId);
        if (company == null) {
            throw new WebApplicationException(String.format("Company with id {0} could not be found.", companyId));
        }
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_OBJECT);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            List<ProductionPlan> productionPlans = company.getProduction().getProductionPlans();
            Set<Product> products = new HashSet<>();
            for (ProductionPlan productionPlan : productionPlans) {
                FinalProduct finalProduct = productionPlan.getFinalProduct();
                products.add(finalProduct);
            }
            mapper.writerWithType(new TypeReference<Set<Product>>() {
            }).writeValue(stream, products);
            return stream.toString("UTF-8");
        } catch (IOException | NullPointerException ex) {
            throw new WebApplicationException("Object could not be encoded.");
        }
    }

    @GET
    @Path("get/products/required/{company-id}")
    @Produces("application/json")
    public String getRequiredProducts(@PathParam("company-id") Long companyId) {
        if (companyId == null) {
            throw new WebApplicationException("Parameter 'game-id' must not be null.");
        }
        Company company = companyFacade.find(companyId);
        if (company == null) {
            throw new WebApplicationException(String.format("Company with id {0} could not be found.", companyId));
        }
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_OBJECT);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            List<ProductionPlan> productionPlans = company.getProduction().getProductionPlans();
            Set<Product> products = new HashSet<>();
            for (ProductionPlan productionPlan : productionPlans) {
                FinalProduct finalProduct = productionPlan.getFinalProduct();
                Set<Part> parts = finalProduct.getPartsList().getParts();
                for (Part part : parts) {
                    products.add(part.getIntermediateProduct());
                }
            }
            mapper.writerWithType(new TypeReference<Set<Product>>() {
            }).writeValue(stream, products);
            return stream.toString("UTF-8");
        } catch (IOException | NullPointerException ex) {
            throw new WebApplicationException("Object could not be encoded.");
        }
    }

}
