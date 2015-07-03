package de.hub.cses.ces.entity.economy;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.economy.EconomicData;
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.market.Market;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(Economy.class)
public class Economy_ extends BaseEntity_ {

    public static volatile SingularAttribute<Economy, Market> market;
    public static volatile SingularAttribute<Economy, Game> game;
    public static volatile SetAttribute<Economy, Company> companies;
    public static volatile SingularAttribute<Economy, EconomicData> economicData;

}