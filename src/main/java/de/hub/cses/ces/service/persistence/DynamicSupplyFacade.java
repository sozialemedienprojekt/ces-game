package de.hub.cses.ces.service.persistence;

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
import de.hub.cses.ces.entity.economy.DynamicSupply;
import de.hub.cses.ces.entity.product.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class DynamicSupplyFacade extends AbstractFacade<DynamicSupply> {

    @PersistenceContext(unitName = "de.hub.cses.ces.PersistenceUnit")
    private EntityManager em;

    /**
     *
     */
    public DynamicSupplyFacade() {
        super(DynamicSupply.class);
    }

    /**
     *
     * @return
     */
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @param product
     * @param lockModeType
     * @return
     */
    public DynamicSupply findLatestSupplyForProduct(Product product, LockModeType lockModeType) {
        TypedQuery<DynamicSupply> query = em.createNamedQuery("DynamicSupply.findByProduct", DynamicSupply.class);
        query.setParameter("product", product);
        query.setMaxResults(1);
        return query.getResultList().get(0);
    }

    /**
     *
     * @param product
     * @return
     */
    public DynamicSupply findLatestSupplyForProduct(Product product) {
        return findLatestSupplyForProduct(product, LockModeType.NONE);
    }

}
