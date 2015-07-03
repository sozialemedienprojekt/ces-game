package de.hub.cses.ces.entity.company.warehouse;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.company.warehouse.StockTransaction;
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import de.hub.cses.ces.entity.product.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(Stock.class)
public class Stock_ extends BaseEntity_ {

    public static volatile SingularAttribute<Stock, Product> product;
    public static volatile SingularAttribute<Stock, Double> quantity;
    public static volatile SingularAttribute<Stock, Warehouse> warehouse;
    public static volatile SetAttribute<Stock, StockTransaction> transactions;

}