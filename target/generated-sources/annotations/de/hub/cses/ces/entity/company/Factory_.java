package de.hub.cses.ces.entity.company;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.text.I18nText;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Factory.class)
public class Factory_ extends BaseEntity_ {

    public static volatile SingularAttribute<Factory, Double> supplyFactor;
    public static volatile SingularAttribute<Factory, Double> overhead;
    public static volatile SingularAttribute<Factory, I18nText> description;
    public static volatile SingularAttribute<Factory, Integer> maxWorkforce;
    public static volatile SingularAttribute<Factory, Integer> coreWorkforce;
    public static volatile SingularAttribute<Factory, Double> buildingCosts;

}