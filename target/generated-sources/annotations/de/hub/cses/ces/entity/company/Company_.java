package de.hub.cses.ces.entity.company;

import de.hub.cses.ces.entity.BaseEntityWithIdentifier_;
import de.hub.cses.ces.entity.company.Factory;
import de.hub.cses.ces.entity.company.accounting.Balance;
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.entity.company.warehouse.Warehouse;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.market.Sector;
import de.hub.cses.ces.entity.production.Production;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(Company.class)
public class Company_ extends BaseEntityWithIdentifier_ {

    public static volatile SingularAttribute<Company, Factory> factory;
    public static volatile SingularAttribute<Company, Balance> balance;
    public static volatile SingularAttribute<Company, Production> production;
    public static volatile SetAttribute<Company, Cooperator> cooperators;
    public static volatile SingularAttribute<Company, Economy> economy;
    public static volatile SingularAttribute<Company, Warehouse> warehouse;
    public static volatile SingularAttribute<Company, Sector> sector;

}