package de.hub.cses.ces.service.persistence.client;

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
import de.hub.cses.ces.entity.client.Client;
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
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "de.hub.cses.ces.PersistenceUnit")
    private EntityManager em;

    /**
     *
     */
    public ClientFacade() {
        super(Client.class);
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
     * @param clientname
     * @return
     */
    public Client findByName(String clientname) {
        TypedQuery<Client> query = em.createNamedQuery("Client.findByClientname", Client.class);
        query.setParameter("clientname", clientname);
        Client client = query.getSingleResult();
        return client;
    }

    /**
     *
     * @param clientname
     * @return
     */
    public boolean exists(String clientname) {
        if (clientname == null) {
            return false;
        }
        TypedQuery<Client> query = em.createNamedQuery("Client.findByClientname", Client.class);
        query.setParameter("clientname", clientname);
        return (query.getResultList().size() > 0);
    }
}
