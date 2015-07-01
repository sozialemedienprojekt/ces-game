package de.hub.cses.ces.entity.product;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.product.IntermediateProduct;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(Part.class)
public class Part_ extends BaseEntity_ {

    public static volatile SingularAttribute<Part, Double> quantity;
    public static volatile SingularAttribute<Part, IntermediateProduct> intermediateProduct;

}