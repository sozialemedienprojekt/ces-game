package de.hub.cses.ces.entity.company.accounting;

import de.hub.cses.ces.entity.product.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(ProductTransaction.class)
public abstract class ProductTransaction_ extends BalanceTransaction_ {

    public static volatile SingularAttribute<ProductTransaction, Product> product;
    public static volatile SingularAttribute<ProductTransaction, Integer> quantity;
    public static volatile SingularAttribute<ProductTransaction, Double> pricePerUnit;

}