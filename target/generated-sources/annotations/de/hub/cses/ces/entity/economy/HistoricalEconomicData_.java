package de.hub.cses.ces.entity.economy;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.economy.DynamicDemand;
import de.hub.cses.ces.entity.economy.DynamicSupply;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(HistoricalEconomicData.class)
public class HistoricalEconomicData_ extends BaseEntity_ {

    public static volatile CollectionAttribute<HistoricalEconomicData, DynamicDemand> dynamicDemands;
    public static volatile CollectionAttribute<HistoricalEconomicData, DynamicSupply> dynamicSupplies;

}