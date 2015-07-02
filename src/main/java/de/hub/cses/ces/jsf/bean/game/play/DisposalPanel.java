package de.hub.cses.ces.jsf.bean.game.play;

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
import de.hub.cses.ces.entity.economy.DynamicDemand;
import de.hub.cses.ces.exception.GameException;
import de.hub.cses.ces.exception.TradingException;
import de.hub.cses.ces.jsf.bean.game.PlayBean;
import de.hub.cses.ces.service.persistence.DynamicDemandFacade;
import de.hub.cses.ces.service.processor.TradingService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("DisposalPanel")
@ViewScoped
public class DisposalPanel implements Serializable {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @Inject
    private PlayBean gamePlay;

    @EJB
    private TradingService tradingService;

    @EJB
    private DynamicDemandFacade dynamicDemandFacade;

    private DynamicDemand demand;
    private int quantity = 0;
    private boolean strictPrice;
    private boolean strictQuantity;

    private int priceVariationIndicator = 0;
    private int quantityVariationIndicator = 0;

    /**
     *
     */
    public DisposalPanel() {

    }

    /**
     *
     * @return
     */
    public DynamicDemand getDemand() {
        return demand;
        //return demand;
    }

    /**
     *
     * @param demand
     */
    public void setDemand(DynamicDemand demand) {
        reset();
        this.demand = demand;
    }

    /**
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public boolean isStrictPrice() {
        return strictPrice;
    }

    /**
     *
     * @param strictPrice
     */
    public void setStrictPrice(boolean strictPrice) {
        this.strictPrice = strictPrice;
    }

    /**
     *
     * @return
     */
    public boolean isStrictQuantity() {
        return strictQuantity;
    }

    /**
     *
     * @param strictQuantity
     */
    public void setStrictQuantity(boolean strictQuantity) {
        this.strictQuantity = strictQuantity;
    }

    /**
     *
     * @return
     */
    public int getPriceVariationIndicator() {
        return priceVariationIndicator;
    }

    /**
     *
     * @param priceVariationIndicator
     */
    public void setPriceVariationIndicator(int priceVariationIndicator) {
        this.priceVariationIndicator = priceVariationIndicator;
    }

    /**
     *
     * @return
     */
    public int getQuantityVariationIndicator() {
        return quantityVariationIndicator;
    }

    /**
     *
     * @param quantityVariationIndicator
     */
    public void setQuantityVariationIndicator(int quantityVariationIndicator) {
        this.quantityVariationIndicator = quantityVariationIndicator;
    }

    /**
     *
     */
    public void cancel() {
        logger.log(Level.INFO, "cancel");
        reset();
    }

    /**
     *
     */
    public void change() {
        logger.log(Level.INFO, "change");
    }

    /**
     *
     * @throws TradingException
     * @throws GameException
     */
    public void dispose() throws TradingException, GameException, NullPointerException {
        logger.log(Level.INFO, "dispose");
        try {
            tradingService.dispose(
                    gamePlay.getGameId(),
                    gamePlay.getCooperator().getCompany().getId(),
                    demand.getProduct().getId(),
                    quantity,
                    demand.getPricePerUnit(),
                    gamePlay.getCurrentDate(),
                    strictPrice,
                    strictQuantity);
        } finally {
            reset();
        }
    }

    private void reset() {
        this.demand = null;
        this.quantity = 0;
        this.strictPrice = false;
        this.strictQuantity = false;
        this.priceVariationIndicator = 0;
        this.quantityVariationIndicator = 0;
    }

    /**
     *
     */
    public void update() {
        if (demand != null) {
            double price = demand.getPricePerUnit();
            int availableQuantity = demand.getAvailableQuantity();
            demand = dynamicDemandFacade.find(demand.getId());
            if (demand != null) {
                int _availableQuantity = demand.getAvailableQuantity();
                quantity = Math.min(quantity, _availableQuantity);
                quantityVariationIndicator = Integer.compare(_availableQuantity, availableQuantity);
                double _price = demand.getPricePerUnit();
                priceVariationIndicator = Double.compare(_price, price);
            }
        }
    }

}
