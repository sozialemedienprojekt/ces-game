package de.hub.cses.ces.entity.market;

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
import de.hub.cses.ces.entity.BaseEntity;
import de.hub.cses.ces.entity.company.Factory;
import de.hub.cses.ces.entity.product.FinalProduct;
import de.hub.cses.ces.entity.product.IntermediateProduct;
import de.hub.cses.ces.entity.text.I18nText;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "market_sector_tbl")
public class Sector extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "market")
    private Market market;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "market_sector_factory_mapping_tbl", joinColumns = {
        @JoinColumn(name = "sector", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "factory", referencedColumnName = "id")})
    private Set<Factory> factories;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "market_sector_intermediate_product_mapping_tbl", joinColumns = {
        @JoinColumn(name = "sector", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "intermediate_product", referencedColumnName = "id")})
    private Set<IntermediateProduct> intermediateProducts;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "market_sector_final_product_mapping_tbl", joinColumns = {
        @JoinColumn(name = "sector", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "final_product", referencedColumnName = "id")})
    private Set<FinalProduct> finalProducts;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "description")
    private I18nText description;

    /**
     *
     */
    public Sector() {
        super();
    }

    /**
     *
     * @param id
     * @param factories
     * @param intermediateProducts
     * @param finalProducts
     * @param description
     */
    public Sector(Long id, Set<Factory> factories, Set<IntermediateProduct> intermediateProducts, Set<FinalProduct> finalProducts, I18nText description) {
        super(id);
        this.factories = factories;
        this.intermediateProducts = intermediateProducts;
        this.finalProducts = finalProducts;
        this.description = description;
    }

    /**
     *
     * @return
     */
    public Market getMarket() {
        return market;
    }

    /**
     *
     * @param market
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     *
     * @return
     */
    public Set<Factory> getFactories() {
        return factories;
    }

    /**
     *
     * @param factories
     */
    public void setFactories(Set<Factory> factories) {
        this.factories = factories;
    }

    /**
     *
     * @return
     */
    public Set<IntermediateProduct> getIntermediateProducts() {
        return intermediateProducts;
    }

    /**
     *
     * @param intermediateProducts
     */
    public void setIntermediateProducts(Set<IntermediateProduct> intermediateProducts) {
        this.intermediateProducts = intermediateProducts;
    }

    /**
     *
     * @return
     */
    public Set<FinalProduct> getFinalProducts() {
        return finalProducts;
    }

    /**
     *
     * @param finalProducts
     */
    public void setFinalProducts(Set<FinalProduct> finalProducts) {
        this.finalProducts = finalProducts;
    }

    /**
     *
     * @return
     */
    public I18nText getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(I18nText description) {
        this.description = description;
    }
}
