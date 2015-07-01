package de.hub.cses.ces.entity.market;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.product.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Supply.class)
public class Supply_ extends BaseEntity_ {

    public static volatile SingularAttribute<Supply, Product> product;
    public static volatile SingularAttribute<Supply, Integer> quantity;

}