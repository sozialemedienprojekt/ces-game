package de.hub.cses.ces.entity.company.cooperator;

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
import de.hub.cses.ces.entity.client.User;
import de.hub.cses.ces.entity.company.Company;
import de.hub.cses.ces.entity.company.cooperator.role.Role;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "company_cooperator_tbl")
@NamedQueries({
    @NamedQuery(name = "Cooperator.findByUsername", query = "SELECT c FROM Cooperator c WHERE c.user.clientname = :username"),
    @NamedQuery(name = "Cooperator.findByUserAndCompany", query = "SELECT c FROM Cooperator c WHERE c.user = :user AND c.company = :company")
})
public class Cooperator extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToMany
    @JoinTable(name = "company_cooperator_role_mapping_tbl", joinColumns = {
        @JoinColumn(name = "cooperator", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "role", referencedColumnName = "id")})
    private Set<Role> roles;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    /**
     *
     */
    public Cooperator() {
        super();
    }

    /**
     *
     * @param id
     * @param user
     * @param company
     */
    public Cooperator(Long id, User user, Company company) {
        super(id);
        this.user = user;
        this.company = company;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     *
     * @return
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     *
     * @param company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     *
     * @return
     */
    public Company getCompany() {
        return company;
    }

}
