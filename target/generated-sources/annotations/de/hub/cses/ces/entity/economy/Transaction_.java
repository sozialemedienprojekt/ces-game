package de.hub.cses.ces.entity.economy;

import de.hub.cses.ces.entity.BaseEntity_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Transaction.class)
public abstract class Transaction_ extends BaseEntity_ {

    public static volatile SingularAttribute<Transaction, Double> amountPosted;
    public static volatile SingularAttribute<Transaction, Date> posted;

}