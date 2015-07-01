package de.hub.cses.ces.entity.economy;

import de.hub.cses.ces.entity.market.Supply_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(DynamicSupply.class)
public class DynamicSupply_ extends Supply_ {

    public static volatile SingularAttribute<DynamicSupply, Integer> availableQuantity;
    public static volatile SingularAttribute<DynamicSupply, Date> calculated;
    public static volatile SingularAttribute<DynamicSupply, Double> pricePerUnit;

}