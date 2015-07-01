package de.hub.cses.ces.service.processor;

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
import de.hub.cses.ces.entity.company.accounting.BalanceTransaction;
import de.hub.cses.ces.entity.company.accounting.Disposal;
import de.hub.cses.ces.entity.company.accounting.ProductTransaction;
import de.hub.cses.ces.entity.company.accounting.Purchase;
import de.hub.cses.ces.entity.company.warehouse.Stock;
import de.hub.cses.ces.entity.economy.DynamicDemand;
import de.hub.cses.ces.entity.economy.DynamicSupply;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.product.Product;
import de.hub.cses.ces.entity.text.I18nText;
import de.hub.cses.ces.entity.text.SupportedLanguage;
import de.hub.cses.ces.exception.GameException;
import de.hub.cses.ces.exception.TradingException;
import de.hub.cses.ces.jsf.config.GamePlayComponent;
import de.hub.cses.ces.service.persistence.game.GameFacade;
import de.hub.cses.ces.service.simulation.accounting.BalanceTransactionAccounting;
import de.hub.cses.ces.service.simulation.accounting.StockTransactionAccounting;
import de.hub.cses.ces.util.ComponentUpdateUtil;
import de.hub.cses.ces.util.I18nTextUtil;
import de.hub.cses.ces.util.NotificationUtil;
import de.hub.cses.ces.util.StocksUtil;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class TradingService {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    private static final I18nText[] exMessages = new I18nText[7];

    static {
        exMessages[0] = new I18nText() {
            {
                this.addText(SupportedLanguage.DEUTSCH, "Preis hat sich zwischenzeitlich geändert.");
                this.addText(SupportedLanguage.ENGLISH, "Price has changed during transaction.");
            }
        };
        exMessages[1] = new I18nText() {
            {
                this.addText(SupportedLanguage.DEUTSCH, "Gewünschte Menge ist nicht mehr verfügbar.");
                this.addText(SupportedLanguage.ENGLISH, "Required quantity is no longer available.");
            }
        };
        exMessages[2] = new I18nText() {
            {
                this.addText(SupportedLanguage.DEUTSCH, "Angebot nicht mehr verfügbar.");
                this.addText(SupportedLanguage.ENGLISH, "Supply no longer available.");
            }
        };
        exMessages[3] = new I18nText() {
            {
                this.addText(SupportedLanguage.DEUTSCH, "Produkt nicht auf Lager.");
                this.addText(SupportedLanguage.ENGLISH, "Product is out of stock.");
            }
        };
        exMessages[4] = new I18nText() {
            {
                this.addText(SupportedLanguage.DEUTSCH, "Spiel läuft nicht (mehr).");
                this.addText(SupportedLanguage.ENGLISH, "Game is (currently) not running.");
            }
        };
        exMessages[5] = new I18nText() {
            {
                this.addText(SupportedLanguage.DEUTSCH, "Unternehmen konnte nicht gefunden werden.");
                this.addText(SupportedLanguage.ENGLISH, "Company could not be found.");
            }
        };
        exMessages[6] = new I18nText() {
            {
                this.addText(SupportedLanguage.DEUTSCH, "Produkt konnte nicht gefunden werden.");
                this.addText(SupportedLanguage.ENGLISH, "Product could not be found.");
            }
        };
    }

    @EJB
    private GameFacade gameFacade;

    @Inject
    private StocksUtil stockFinder;

    @Inject
    private BalanceTransactionAccounting balanceTransactionAcccounting;

    @Inject
    private StockTransactionAccounting stockTransactionAccounting;

    @Inject
    private I18nTextUtil i18nTextUtil;

    @Inject
    private NotificationUtil notificationUtil;

    @Inject
    private ComponentUpdateUtil componentUpdateUtil;

    /**
     *
     * @param gameId
     * @param companyId
     * @param productId
     * @param quantity
     * @param price
     * @param current
     * @param strictPrice
     * @param strictQuantity
     * @throws TradingException
     * @throws de.hub.cses.ces.exception.GameException
     */
    public void purchase(Long gameId, Long companyId, Long productId, int quantity, double price, Date current, boolean strictPrice, boolean strictQuantity) throws TradingException, GameException, NullPointerException {
        Game game = gameFacade.find(gameId, LockModeType.PESSIMISTIC_WRITE);
        if (!GameStatus.RUNNING.equals(game.getGameStatus())) {
            throw new GameException(i18nTextUtil.get(exMessages[4]));
        }
        Company company = findCompany(companyId, game);
        if (company == null) {
            throw new GameException(i18nTextUtil.get(exMessages[5]));
        }
        Product product = findProduct(productId, company.getSector());
        if (product == null) {
            throw new TradingException(i18nTextUtil.get(exMessages[6]));
        }
        DynamicSupply dynamicSupply = findDynamicSupply(product, game.getEconomy());
        if (dynamicSupply == null) {
            throw new TradingException(i18nTextUtil.get(exMessages[2]));
        }
        if ((strictPrice && (Double.compare(dynamicSupply.getPricePerUnit(), price) != 0)) || (Double.compare(dynamicSupply.getPricePerUnit(), price) > 0)) {
            throw new TradingException(i18nTextUtil.get(exMessages[0]));
        }
        if (strictQuantity && (dynamicSupply.getQuantity() < quantity)) {
            throw new TradingException(i18nTextUtil.get(exMessages[1]));
        }
        int maxQuantity = Math.min(dynamicSupply.getAvailableQuantity(), quantity);
        if (maxQuantity > 0) {
            double costs = dynamicSupply.getPricePerUnit() * maxQuantity;
            dynamicSupply.setAvailableQuantity(dynamicSupply.getAvailableQuantity() - maxQuantity);
            BalanceTransaction balanceTransaction = balanceTransactionAcccounting.account(company.getBalance(), -costs, current, Purchase.class);
            ((ProductTransaction) balanceTransaction).setProduct(product);
            ((ProductTransaction) balanceTransaction).setPricePerUnit(dynamicSupply.getPricePerUnit());
            ((ProductTransaction) balanceTransaction).setQuantity(maxQuantity);
            Stock stock = stockFinder.find(company.getWarehouse().getStocks(), product);
            stockTransactionAccounting.account(stock, maxQuantity, current);
            //notificationUtil.notifyCompany(companyId, null, NotificationType.INFO);
            componentUpdateUtil.companyUpdate(companyId, GamePlayComponent.BALANCE, GamePlayComponent.STOCKS);
            componentUpdateUtil.gameUpdate(gameId, GamePlayComponent.PURCHASE);
        } else {
            throw new TradingException(i18nTextUtil.get(exMessages[3]));
        }
    }

    /**
     *
     * @param gameId
     * @param companyId
     * @param productId
     * @param quantity
     * @param price
     * @param current
     * @param strictPrice
     * @param strictQuantity
     * @throws TradingException
     * @throws de.hub.cses.ces.exception.GameException
     */
    public void dispose(Long gameId, Long companyId, Long productId, int quantity, double price, Date current, boolean strictPrice, boolean strictQuantity) throws TradingException, GameException, NullPointerException {
        Game game = gameFacade.find(gameId, LockModeType.PESSIMISTIC_WRITE);
        if (!GameStatus.RUNNING.equals(game.getGameStatus())) {
            throw new GameException(i18nTextUtil.get(exMessages[4]));
        }
        Company company = findCompany(companyId, game);
        if (company == null) {
            throw new GameException(i18nTextUtil.get(exMessages[5]));
        }
        Product product = findProduct(productId, company.getSector());
        if (product == null) {
            throw new TradingException(i18nTextUtil.get(exMessages[6]));
        }
        DynamicDemand dynamicDemand = findDynamicDemand(product, game.getEconomy());
        if (dynamicDemand == null) {
            throw new TradingException(i18nTextUtil.get(exMessages[2]));
        }
        if ((strictPrice && (Double.compare(dynamicDemand.getPricePerUnit(), price) != 0)) || (Double.compare(dynamicDemand.getPricePerUnit(), price) < 0)) {
            throw new TradingException(i18nTextUtil.get(exMessages[0]));
        }
        if (strictQuantity && (dynamicDemand.getQuantity() < quantity)) {
            throw new TradingException(i18nTextUtil.get(exMessages[1]));
        }
        Stock stock = stockFinder.find(company.getWarehouse().getStocks(), product);
        if (stock == null) {
            throw new TradingException(i18nTextUtil.get(exMessages[3]));
        }
        int maxQuantity = Math.min((int) stock.getQuantity(), Math.min(dynamicDemand.getAvailableQuantity(), quantity));
        if (maxQuantity > 0) {
            double income = dynamicDemand.getPricePerUnit() * maxQuantity;
            dynamicDemand.setAvailableQuantity(dynamicDemand.getAvailableQuantity() - maxQuantity);
            BalanceTransaction balanceTransaction = balanceTransactionAcccounting.account(company.getBalance(), income, current, Disposal.class);
            ((ProductTransaction) balanceTransaction).setProduct(product);
            ((ProductTransaction) balanceTransaction).setPricePerUnit(dynamicDemand.getPricePerUnit());
            ((ProductTransaction) balanceTransaction).setQuantity(maxQuantity);
            stockTransactionAccounting.account(stock, -maxQuantity, current);
            //notificationUtil.notifyCompany(companyId, null, NotificationType.INFO);
            componentUpdateUtil.companyUpdate(companyId, GamePlayComponent.BALANCE, GamePlayComponent.STOCKS);
            componentUpdateUtil.gameUpdate(gameId, GamePlayComponent.DISPOSAL);
        } else {
            throw new TradingException(i18nTextUtil.get(exMessages[3]));
        }
    }

    private Company findCompany(Long companyId, Game game) {
        if (companyId == null) {
            return null;
        }
        for (Company company : game.getEconomy().getCompanies()) {
            if (company != null && companyId.equals(company.getId())) {
                return company;
            }
        }
        return null;
    }

    private Product findProduct(Long productId, Sector sector) {
        Product product = findFinalProduct(productId, sector);
        if (product == null) {
            product = findIntermediateProduct(productId, sector);
        }
        return product;
    }

    private Product findFinalProduct(Long productId, Sector sector) {
        if (productId == null) {
            return null;
        }
        for (Product product : sector.getFinalProducts()) {
            if (product != null && productId.equals(product.getId())) {
                return product;
            }
        }
        return null;
    }

    private Product findIntermediateProduct(Long productId, Sector sector) {
        if (productId == null) {
            return null;
        }
        for (Product product : sector.getIntermediateProducts()) {
            if (product != null && productId.equals(product.getId())) {
                return product;
            }
        }
        return null;
    }

    private DynamicSupply findDynamicSupply(Product product, Economy economy) {
        if (product == null) {
            return null;
        }
        for (DynamicSupply dynamicSupply : economy.getEconomicData().getDynamicSupplies()) {
            if (product.equals(dynamicSupply.getProduct())) {
                return dynamicSupply;
            }
        }
        return null;
    }

    private DynamicDemand findDynamicDemand(Product product, Economy economy) {
        if (product == null) {
            return null;
        }
        for (DynamicDemand dynamicDemand : economy.getEconomicData().getDynamicDemands()) {
            if (product.equals(dynamicDemand.getProduct())) {
                return dynamicDemand;
            }
        }
        return null;
    }

}
