package de.hub.cses.ces.entity.production;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.production.ProductionPlan;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Production.class)
public class Production_ extends BaseEntity_ {

    public static volatile ListAttribute<Production, ProductionPlan> productionPlans;
    public static volatile SingularAttribute<Production, Company> company;

}