package de.hub.cses.ces.entity.product;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.text.I18nText;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Product.class)
public abstract class Product_ extends BaseEntity_ {

    public static volatile SingularAttribute<Product, I18nText> description;
    public static volatile SingularAttribute<Product, Double> warehouseCharges;

}