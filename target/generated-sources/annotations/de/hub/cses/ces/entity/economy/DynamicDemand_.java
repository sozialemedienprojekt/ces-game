package de.hub.cses.ces.entity.economy;

import de.hub.cses.ces.entity.market.Demand_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(DynamicDemand.class)
public class DynamicDemand_ extends Demand_ {

    public static volatile SingularAttribute<DynamicDemand, Integer> availableQuantity;
    public static volatile SingularAttribute<DynamicDemand, Date> calculated;
    public static volatile SingularAttribute<DynamicDemand, Double> pricePerUnit;

}