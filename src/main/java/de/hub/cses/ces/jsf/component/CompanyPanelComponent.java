/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hub.cses.ces.jsf.component;

/*
 * #%L
 * CES-Game
 * %%
 * Copyright (C) 2015 Humboldt-Universität zu Berlin,
 * Department of Computer Science,
 * Research Group "Computer Science Education / Computer Science and Society"
 * Sebastian Gross <sebastian.gross@hu-berlin.de>
 * Sven Strickroth <sven.strickroth@hu-berlin.de>
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.Part;
import de.hub.cses.ces.entity.product.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@FacesComponent("CompanyPanelComponent")
public class CompanyPanelComponent extends UINamingContainer {

    private FinalProduct product;
    private Cooperator cooperator;
    private Company competitor;

    /**
     *
     */
    public CompanyPanelComponent() {
        super();
    }

    /**
     *
     * @return
     */
    public Cooperator getCooperator() {
        return cooperator;
    }

    /**
     *
     * @param cooperator
     */
    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }

    /**
     *
     * @return
     */
    public Company getCompetitor() {
        return competitor;
    }

    /**
     *
     * @param competitor
     */
    public void setCompetitor(Company competitor) {
        this.competitor = competitor;
    }

    /**
     *
     * @return
     */
    public Product getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(FinalProduct product) {
        this.product = product;
    }

    /**
     *
     * @return
     */
    public Collection<Part> getParts() {
        List<Part> parts = new ArrayList<>();
        if (product == null) {
            return parts;
        }
        parts.addAll(product.getPartsList().getParts());
        Collections.sort(parts);
        return parts;
    }
}
