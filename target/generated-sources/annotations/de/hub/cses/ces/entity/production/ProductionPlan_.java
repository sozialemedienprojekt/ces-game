package de.hub.cses.ces.entity.production;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.production.Production;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(ProductionPlan.class)
public class ProductionPlan_ extends BaseEntity_ {

    public static volatile SingularAttribute<ProductionPlan, Production> production;
    public static volatile SingularAttribute<ProductionPlan, Integer> workforce;
    public static volatile SingularAttribute<ProductionPlan, FinalProduct> finalProduct;

}