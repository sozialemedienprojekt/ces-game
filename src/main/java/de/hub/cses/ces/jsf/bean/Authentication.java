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
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("Authentication")
@RequestScoped
public class Authentication {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    private String username;
    private String password;

    private String originalURL;

    /**
     *
     */
    public Authentication() {

    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
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

    /**
     *
     * @return
     */
    public String login() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest) ec.getRequest();
            request.login(username, password);
            Principal principal = ec.getUserPrincipal();
            logger.log(Level.INFO, "user {0} authenticated", principal.getName());
            String url = (originalURL != null) ? getRedirectURL(originalURL) : getRedirectURL("index.xhtml");
            originalURL = null;
            return url;
        } catch (ServletException e) {
            FacesContext.getCurrentInstance().addMessage("Login",
                    new FacesMessage("Invalid Username/Password combination"));

            return null;

        }
        //return "index.xhtml?faces-redirect=true";
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String url = getRedirectURL("index.xhtml");
        try {
            ((HttpSession) ec.getSession(false)).invalidate();
        } catch (NullPointerException npe) {
            ec.redirect(url);
        }
        return url;
    }

    private String getRedirectURL(String url) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, List<String>> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add("true");
        params.put("faces-redirect", values);
        return ec.encodeRedirectURL(url, params);
    }

    /**
     *
     * @param originalURL
     */
    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    /**
     *
     * @return
     */
    public String getOriginalURL() {
        if (originalURL != null) {
            String tmp = originalURL;
            originalURL = null;
            return tmp;
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return (String) ec.getRequestMap().get(RequestDispatcher.FORWARD_SERVLET_PATH);
    }
}
