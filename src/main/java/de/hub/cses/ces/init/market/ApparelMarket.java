package de.hub.cses.ces.init.market;

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
import de.hub.cses.ces.entity.company.Factory;
import de.hub.cses.ces.entity.market.BaseDemand;
import de.hub.cses.ces.entity.market.BaseSupply;
import de.hub.cses.ces.entity.market.Market;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.IntermediateProduct;
import de.hub.cses.ces.entity.product.Part;
import de.hub.cses.ces.entity.product.PartsList;
import de.hub.cses.ces.entity.text.I18nText;
import de.hub.cses.ces.entity.text.SupportedLanguage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
public final class ApparelMarket {

    // <editor-fold defaultstate="collapsed" desc="intermediate product descriptions">
    private static final I18nText[] intermediateProductDescriptions = {
        new I18nText(1L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Prozessor");
                put(SupportedLanguage.ENGLISH, "Processor");
            }
        }),
        new I18nText(2L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Platine");
                put(SupportedLanguage.ENGLISH, "Board");
            }
        }),
        new I18nText(3L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Speicher");
                put(SupportedLanguage.ENGLISH, "Memory");
            }
        }),
        new I18nText(4L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Bildschirm");
                put(SupportedLanguage.ENGLISH, "Display");
            }
        })
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="intermediate products">
    private static final IntermediateProduct[] intermediateProducts = {
        // define cpu
        new IntermediateProduct(1L, intermediateProductDescriptions[0], 0.02, 20d),
        // define board
        new IntermediateProduct(2L, intermediateProductDescriptions[1], 0.04, 5d),
        // define memory
        new IntermediateProduct(3L, intermediateProductDescriptions[2], 0.01, 10d),
        // define display
        new IntermediateProduct(4L, intermediateProductDescriptions[3], 0.06, 15d)
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="parts lists">
    private static final PartsList[] partsLists = {
        // define parts list for television (small)
        new PartsList(1L, new HashSet<Part>() {
            {
                add(new Part(1L, intermediateProducts[0], 1));
                add(new Part(2L, intermediateProducts[1], 4));
                add(new Part(3L, intermediateProducts[2], 1));
                add(new Part(4L, intermediateProducts[3], 8));
            }
        }),
        // define parts list for television (medium)
        new PartsList(2L, new HashSet<Part>() {
            {
                add(new Part(5L, intermediateProducts[0], 1));
                add(new Part(6L, intermediateProducts[1], 4));
                add(new Part(7L, intermediateProducts[2], 1));
                add(new Part(8L, intermediateProducts[3], 16));
            }
        }),
        // define parts list for television (large)
        new PartsList(3L, new HashSet<Part>() {
            {
                add(new Part(9L, intermediateProducts[0], 1));
                add(new Part(10L, intermediateProducts[1], 4));
                add(new Part(11L, intermediateProducts[2], 1));
                add(new Part(12L, intermediateProducts[3], 32));
            }
        }),
        // define parts list for laptop (slow)
        new PartsList(4L, new HashSet<Part>() {
            {
                add(new Part(13L, intermediateProducts[0], 4));
                add(new Part(14L, intermediateProducts[1], 4));
                add(new Part(15L, intermediateProducts[2], 4));
                add(new Part(16L, intermediateProducts[3], 6));
            }
        }),
        // define parts list for laptop (normal)
        new PartsList(5L, new HashSet<Part>() {
            {
                add(new Part(17L, intermediateProducts[0], 8));
                add(new Part(18L, intermediateProducts[1], 4));
                add(new Part(19L, intermediateProducts[2], 8));
                add(new Part(20L, intermediateProducts[3], 6));
            }
        }),
        // define parts list for laptop (fast)
        new PartsList(6L, new HashSet<Part>() {
            {
                add(new Part(21L, intermediateProducts[0], 16));
                add(new Part(22L, intermediateProducts[1], 4));
                add(new Part(23L, intermediateProducts[2], 16));
                add(new Part(24L, intermediateProducts[3], 6));
            }
        }),
        // define parts list for mobile phone (simple)
        new PartsList(7L, new HashSet<Part>() {
            {
                add(new Part(25L, intermediateProducts[0], 1));
                add(new Part(26L, intermediateProducts[1], 1));
                add(new Part(27L, intermediateProducts[2], 1));
                add(new Part(28L, intermediateProducts[3], 1));
            }
        }),
        // define parts list for mobile phone (normal)
        new PartsList(8L, new HashSet<Part>() {
            {
                add(new Part(29L, intermediateProducts[0], 2));
                add(new Part(30L, intermediateProducts[1], 1));
                add(new Part(31L, intermediateProducts[2], 2));
                add(new Part(32L, intermediateProducts[3], 1));
            }
        }),
        // define parts list for mobile phone (smart)
        new PartsList(9L, new HashSet<Part>() {
            {
                add(new Part(33L, intermediateProducts[0], 4));
                add(new Part(34L, intermediateProducts[1], 1));
                add(new Part(35L, intermediateProducts[2], 4));
                add(new Part(36L, intermediateProducts[3], 1));
            }
        })
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="final product descriptions">
    private static final I18nText[] finalProductDescriptions = {
        new I18nText(5L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Fernseher (klein)");
                put(SupportedLanguage.ENGLISH, "Television (small)");
            }
        }),
        new I18nText(6L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Fernseher (mittel)");
                put(SupportedLanguage.ENGLISH, "Television (medium)");
            }
        }),
        new I18nText(7L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Fernseher (groß)");
                put(SupportedLanguage.ENGLISH, "Television (large)");
            }
        }),
        new I18nText(8L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Laptop (langsam)");
                put(SupportedLanguage.ENGLISH, "Laptop (slow)");
            }
        }),
        new I18nText(9L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Laptop (normal)");
                put(SupportedLanguage.ENGLISH, "Laptop (normal)");
            }
        }),
        new I18nText(10L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Laptop (schnell)");
                put(SupportedLanguage.ENGLISH, "Laptop (fast)");
            }
        }),
        new I18nText(11L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Handy (einfach)");
                put(SupportedLanguage.ENGLISH, "Mobile Phone (simple)");
            }
        }),
        new I18nText(12L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Handy (normal)");
                put(SupportedLanguage.ENGLISH, "Mobile Phone (normal)");
            }
        }),
        new I18nText(13L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Handy (smart)");
                put(SupportedLanguage.ENGLISH, "Mobile Phone (smart)");
            }
        })};
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="final products">
    private static final FinalProduct[] finalProducts = {
        // define televisions
        new FinalProduct(5L, finalProductDescriptions[0], 0.45d, partsLists[0], 8d, 190d),
        new FinalProduct(6L, finalProductDescriptions[1], 0.6d, partsLists[1], 16d, 330d),
        new FinalProduct(7L, finalProductDescriptions[2], 0.75d, partsLists[2], 24d, 615d),
        // define laptops
        new FinalProduct(8L, finalProductDescriptions[3], 0.3d, partsLists[3], 12d, 265d),
        new FinalProduct(9L, finalProductDescriptions[4], 0.4d, partsLists[4], 20d, 405d),
        new FinalProduct(10L, finalProductDescriptions[5], 0.5d, partsLists[5], 28d, 675d),
        // define mobile phones
        new FinalProduct(11L, finalProductDescriptions[6], 0.1d, partsLists[6], 3d, 57d),
        new FinalProduct(12L, finalProductDescriptions[7], 0.15d, partsLists[7], 6d, 90d),
        new FinalProduct(13L, finalProductDescriptions[8], 0.2d, partsLists[8], 9d, 150d)
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="factory descriptions">
    private static final I18nText[] factoryDescriptions = {
        new I18nText(14L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Kleine Fabrik");
                put(SupportedLanguage.ENGLISH, "Small factory");
            }
        }),
        new I18nText(15L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Mittelgroße Fabrik");
                put(SupportedLanguage.ENGLISH, "Medium factory");
            }
        }),
        new I18nText(16L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Große Fabrik");
                put(SupportedLanguage.ENGLISH, "Large factory");
            }
        })
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="factories">
    private static final Factory[] factories = {
        // define small factory
        new Factory(1L, 150000, 5000d, 0.7, 80, 120, factoryDescriptions[0]),
        // define medium factory
        new Factory(2L, 250000, 10000d, 1.0, 120, 200, factoryDescriptions[1]),
        // define large factory
        new Factory(3L, 350000, 15000d, 1.3, 160, 280, factoryDescriptions[2])
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sector descriptions">
    private static final I18nText[] sectorDescriptions = {
        new I18nText(17L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Marktsektor für Fernseher");
                put(SupportedLanguage.ENGLISH, "Market sector for televisions");
            }
        }),
        new I18nText(18L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Marktsektor für Laptops");
                put(SupportedLanguage.ENGLISH, "Market sector for laptops");
            }
        }),
        new I18nText(19L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Marktsektor for Mobilfunkgeräte");
                put(SupportedLanguage.ENGLISH, "Market sector for mobile phones");
            }
        })
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sectors">
    private static final Sector[] sectors = {
        // define sector for televisions
        new Sector(1L, new HashSet<Factory>() {
            {
                add(factories[0]);
                add(factories[1]);
                add(factories[2]);
            }
        }, new HashSet<IntermediateProduct>() {
            {
                add(intermediateProducts[0]);
                add(intermediateProducts[1]);
                add(intermediateProducts[2]);
                add(intermediateProducts[3]);
            }
        }, new HashSet<FinalProduct>() {
            {
                add(finalProducts[0]);
                add(finalProducts[1]);
                add(finalProducts[2]);
            }
        }, sectorDescriptions[0]),
        // define sector for laptops
        new Sector(2L, new HashSet<Factory>() {
            {
                add(factories[0]);
                add(factories[1]);
                add(factories[2]);
            }
        }, new HashSet<IntermediateProduct>() {
            {
                add(intermediateProducts[0]);
                add(intermediateProducts[1]);
                add(intermediateProducts[2]);
                add(intermediateProducts[3]);
            }
        }, new HashSet<FinalProduct>() {
            {
                add(finalProducts[3]);
                add(finalProducts[4]);
                add(finalProducts[5]);
            }
        }, sectorDescriptions[1]),
        // define sector for mobile phones
        new Sector(3L, new HashSet<Factory>() {
            {
                add(factories[0]);
                add(factories[1]);
                add(factories[2]);
            }
        }, new HashSet<IntermediateProduct>() {
            {
                add(intermediateProducts[0]);
                add(intermediateProducts[1]);
                add(intermediateProducts[2]);
                add(intermediateProducts[3]);
            }
        }, new HashSet<FinalProduct>() {
            {
                add(finalProducts[6]);
                add(finalProducts[7]);
                add(finalProducts[8]);
            }
        }, sectorDescriptions[2])
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="private no-arg constructor">
    private ApparelMarket() {
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static create method">

    /**
     *
     * @return
     */
        public static Market create() {
        Set<BaseSupply> baseSupplies = new HashSet() {
            {
                // cpu
                add(new BaseSupply(1L, intermediateProducts[0], 3700));
                // board
                add(new BaseSupply(2L, intermediateProducts[1], 3000));
                // memory
                add(new BaseSupply(3L, intermediateProducts[2], 3700));
                // display
                add(new BaseSupply(4L, intermediateProducts[3], 6300));
            }
        };
        Set<BaseDemand> baseDemands = new HashSet() {
            {
                // tv (small)
                add(new BaseDemand(1L, finalProducts[0], 400));
                // tv (normal)
                add(new BaseDemand(2L, finalProducts[1], 440));
                // tv (large)
                add(new BaseDemand(3L, finalProducts[2], 70));
                // laptop (slow)
                add(new BaseDemand(4L, finalProducts[3], 180));
                // laptop (normal)
                add(new BaseDemand(5L, finalProducts[4], 240));
                // laptop (fast)
                add(new BaseDemand(6L, finalProducts[5], 170));
                // mobile phone (simple)
                add(new BaseDemand(7L, finalProducts[6], 1800));
                // mobile phone (normal)
                add(new BaseDemand(8L, finalProducts[7], 860));
                // mobile phone (smart)
                add(new BaseDemand(9L, finalProducts[8], 300));
            }
        };
        Market market = new Market(1L, 15d, 8d, new HashSet<Sector>() {
            {
                add(sectors[0]);
                add(sectors[1]);
                add(sectors[2]);
            }
        }, baseSupplies, baseDemands, new I18nText(20L, new HashMap<SupportedLanguage, String>() {
            {
                put(SupportedLanguage.DEUTSCH, "Markt für Unterhaltungselektronik");
                put(SupportedLanguage.ENGLISH, "Market for home electronics");
            }
        }
        ));
        for (Sector sector : sectors) {
            sector.setMarket(market);
        }
        for (BaseDemand baseDemand : baseDemands) {
            baseDemand.setMarket(market);
        }
        for (BaseSupply baseSupply : baseSupplies) {
            baseSupply.setMarket(market);
        }
        return market;
    }
    // </editor-fold>
}
