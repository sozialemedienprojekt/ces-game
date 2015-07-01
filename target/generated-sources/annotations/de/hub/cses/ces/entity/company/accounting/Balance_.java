package de.hub.cses.ces.entity.company.accounting;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.accounting.BalanceTransaction;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Balance.class)
public class Balance_ extends BaseEntity_ {

    public static volatile SingularAttribute<Balance, Double> amount;
    public static volatile SingularAttribute<Balance, Company> company;
    public static volatile SetAttribute<Balance, BalanceTransaction> transactions;

}