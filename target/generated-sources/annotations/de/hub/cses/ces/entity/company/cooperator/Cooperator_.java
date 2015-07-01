package de.hub.cses.ces.entity.company.cooperator;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.client.User;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.cooperator.role.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Cooperator.class)
public class Cooperator_ extends BaseEntity_ {

    public static volatile SetAttribute<Cooperator, Role> roles;
    public static volatile SingularAttribute<Cooperator, Company> company;
    public static volatile SingularAttribute<Cooperator, User> user;

}