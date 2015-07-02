package de.hub.cses.ces.service.persistence.company.cooperator;

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
import de.hub.cses.ces.entity.client.User;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.cooperator.Cooperator;
import de.hub.cses.ces.service.persistence.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Stateless
public class CooperatorFacade extends AbstractFacade<Cooperator> {

    @PersistenceContext(unitName = "de.hub.cses.ces.PersistenceUnit")
    private EntityManager em;

    /**
     *
     */
    public CooperatorFacade() {
        super(Cooperator.class);
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
     * @param username
     * @return
     */
    public Cooperator findByUsername(String username) {
        Cooperator cooperator = null;
        try {
            TypedQuery<Cooperator> query = em.createNamedQuery("Cooperator.findByUsername", Cooperator.class);
            query.setParameter("username", username);
            cooperator = query.getSingleResult();
        } catch (Exception e) {

        }
        return cooperator;
    }

    /**
     *
     * @param user
     * @param company
     * @return
     */
    public Cooperator findByUserAndCompany(User user, Company company) {
        Cooperator cooperator = null;
        try {
            TypedQuery<Cooperator> query = em.createNamedQuery("Cooperator.findByUserAndCompany", Cooperator.class);
            query.setParameter("user", user);
            query.setParameter("company", company);
            cooperator = query.getSingleResult();
        } catch (Exception e) {

        }
        return cooperator;
    }
}
