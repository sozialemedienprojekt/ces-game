package de.hub.cses.ces.entity.market;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.company.Factory;
import de.hub.cses.ces.entity.market.Market;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.IntermediateProduct;
import de.hub.cses.ces.entity.text.I18nText;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(Sector.class)
public class Sector_ extends BaseEntity_ {

    public static volatile SingularAttribute<Sector, Market> market;
    public static volatile SetAttribute<Sector, FinalProduct> finalProducts;
    public static volatile SingularAttribute<Sector, I18nText> description;
    public static volatile SetAttribute<Sector, Factory> factories;
    public static volatile SetAttribute<Sector, IntermediateProduct> intermediateProducts;

}