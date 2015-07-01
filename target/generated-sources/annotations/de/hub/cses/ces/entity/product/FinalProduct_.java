package de.hub.cses.ces.entity.product;

import de.hub.cses.ces.entity.product.PartsList;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(FinalProduct.class)
public class FinalProduct_ extends Product_ {

    public static volatile SingularAttribute<FinalProduct, Double> requiredWorkingTime;
    public static volatile SingularAttribute<FinalProduct, Double> baseRetailPrice;
    public static volatile SingularAttribute<FinalProduct, PartsList> partsList;

}