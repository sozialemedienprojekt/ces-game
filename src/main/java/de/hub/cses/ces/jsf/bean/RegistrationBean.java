package de.hub.cses.ces.jsf.bean;

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
import de.hub.cses.ces.service.persistence.client.ClientFacade;
import de.hub.cses.ces.util.qualifier.Instantiate;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("RegistrationBean")
@RequestScoped
public class RegistrationBean {

    @EJB
    private ClientFacade clientFacade;

    @Inject
    @Instantiate
    private User user;

    private InputText username;

    /**
     *
     */
    public RegistrationBean() {
    }

    /**
     *
     * @return
     */
    public InputText getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(InputText username) {
        this.username = username;
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
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @return
     */
    public String register() {
        if (clientFacade.exists(user.getClientname())) {
            String errorMessage = FacesContext.getCurrentInstance().getApplication().
                    getResourceBundle(FacesContext.getCurrentInstance(), "i18n_txts").
                    getString("exists");
            // Add View Faces Message
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    errorMessage, errorMessage);
            // Add the message into context for a specific component
            FacesContext.getCurrentInstance().addMessage(username.getClientId(), message);
            return null;
        }
        String password = user.getPassword();
        user.setPassword(DigestUtils.sha512Hex(password));
        clientFacade.create(user);
        return "index?faces-redirect=true";
    }
}
