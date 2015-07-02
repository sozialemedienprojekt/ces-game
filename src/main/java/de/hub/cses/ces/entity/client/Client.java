package de.hub.cses.ces.entity.client;

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
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "client_tbl")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "client_type")
@NamedQuery(name = "Client.findByClientname", query = "SELECT c FROM Client c WHERE c.clientname = :clientname")
public abstract class Client extends BaseEntity {

    @Column(name = "clientname", unique = true)
    @Size(min = 5, max = 25, message = "{username} {too_short}")
    @NotNull(message = "{username} {not_empty}")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "{username} {wrong_characters}")
    private String clientname;
    @Column(name = "password")
    @Size(min = 5, message = "{password} {too_short}")
    @NotNull(message = "{password} {not_empty}")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "{password} {wrong_characters}")
    private String password;

    /**
     *
     */
    protected Client() {
        super();
    }

    /**
     *
     * @param id
     * @param clientname
     * @param password
     */
    protected Client(Long id, String clientname, String password) {
        super(id);
        this.clientname = clientname;
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getClientname() {
        return clientname;
    }

    /**
     *
     * @param clientname
     */
    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
