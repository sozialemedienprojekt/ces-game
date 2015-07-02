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
import de.hub.cses.ces.entity.client.User;
import de.hub.cses.ces.util.qualifier.Identify;
import de.hub.cses.ces.util.qualifier.Instantiate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("UserUtil")
@Dependent
public class UserUtil extends IdentityUtil<User> {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    @Inject
    private ClientUtil clientUtil;

    /**
     *
     */
    public UserUtil() {
        super(User.class);
    }

    /**
     *
     * @param ip
     * @return
     */
    @Override
    @Produces
    @Identify
    public User identify(InjectionPoint ip) {
        Client client = clientUtil.identify();
        if (client != null && User.class.isAssignableFrom(client.getClass())) {
            return (User) client;
        }
        return null;
    }

    /**
     *
     * @param ip
     * @return
     */
    @Override
    @Produces
    @Instantiate
    public User instantiate(InjectionPoint ip) {
        User user = null;
        try {
            user = super.instantiate(ip);
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
