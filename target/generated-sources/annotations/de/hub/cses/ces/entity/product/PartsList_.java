package de.hub.cses.ces.entity.product;

import de.hub.cses.ces.entity.BaseEntity_;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.Part;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-01T14:34:19")
@StaticMetamodel(PartsList.class)
public class PartsList_ extends BaseEntity_ {

    public static volatile SetAttribute<PartsList, Part> parts;
    public static volatile SingularAttribute<PartsList, FinalProduct> finalProduct;

}