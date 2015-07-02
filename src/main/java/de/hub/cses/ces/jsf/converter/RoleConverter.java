package de.hub.cses.ces.jsf.converter;

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

import de.hub.cses.ces.entity.company.cooperator.role.Role;
import de.hub.cses.ces.jsf.bean.game.SetupBean;
import java.util.Collection;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@FacesConverter("roleConverter")
public final class RoleConverter extends AbstractEntityConverter<Role> {

    @Inject
    private SetupBean gameSetup;

    /**
     *
     */
    public RoleConverter() {
        super(Role.class);
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<Role> getEntities() {
        return gameSetup.getRoles();
    }
}
