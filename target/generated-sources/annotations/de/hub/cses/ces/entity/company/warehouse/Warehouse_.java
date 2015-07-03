package de.hub.cses.ces.entity.company.warehouse;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.warehouse.Stock;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(Warehouse.class)
public class Warehouse_ extends BaseEntity_ {

    public static volatile SingularAttribute<Warehouse, Company> company;
    public static volatile ListAttribute<Warehouse, Stock> stocks;

}