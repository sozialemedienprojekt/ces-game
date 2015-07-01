package de.hub.cses.ces.entity.economy;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.economy.DynamicDemand;
import de.hub.cses.ces.entity.economy.DynamicSupply;
import de.hub.cses.ces.entity.economy.HistoricalEconomicData;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(EconomicData.class)
public class EconomicData_ extends BaseEntity_ {

    public static volatile SingularAttribute<EconomicData, HistoricalEconomicData> historicalEconomicData;
    public static volatile CollectionAttribute<EconomicData, DynamicDemand> dynamicDemands;
    public static volatile CollectionAttribute<EconomicData, DynamicSupply> dynamicSupplies;

}