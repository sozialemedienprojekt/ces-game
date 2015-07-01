package de.hub.cses.ces.entity.market;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.market.BaseDemand;
import de.hub.cses.ces.entity.market.BaseSupply;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.text.I18nText;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Market.class)
public class Market_ extends BaseEntity_ {

    public static volatile SetAttribute<Market, Sector> sectors;
    public static volatile SetAttribute<Market, BaseSupply> baseSupplies;
    public static volatile SetAttribute<Market, BaseDemand> baseDemands;
    public static volatile SingularAttribute<Market, Double> hourlyWage;
    public static volatile SingularAttribute<Market, Double> workingHoursPerDay;
    public static volatile SingularAttribute<Market, I18nText> description;

}