package de.hub.cses.ces.jsf.bean.util;

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
import de.hub.cses.ces.service.persistence.client.ClientFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named
@Dependent
public class ClientUtil {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @EJB
    private ClientFacade clientFacade;

    /**
     *
     * @return
     */
    public Client identify() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String clientname = ec.getUserPrincipal().getName();
            Client client = clientFacade.findByName(clientname);
            return client;
        } catch (NullPointerException npe) {
            logger.log(Level.WARNING, null, npe);
        }
        return null;
    }

}
