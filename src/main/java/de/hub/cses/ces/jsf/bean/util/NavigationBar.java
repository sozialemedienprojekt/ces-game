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
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.menu.Menu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("NavigationBar")
@ApplicationScoped
public class NavigationBar {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger logger;

    @Inject
    private PropertyResourceBundle bundle;

    private Menu menu;

    /**
     *
     */
    @PostConstruct()
    public void init() {
        menu = new Menu();
        menu.setTransient(false);
        menu.setStyleClass("ui-menubar");
        menu.setStyle("width:auto;");
        MenuModel model = new DefaultMenuModel();
        try {
            model.addElement(new DefaultMenuItem(bundle.getString("link_register_new_player"), "fa fa-plus", "/register.xhtml"));
            model.addElement(new DefaultMenuItem(bundle.getString("link_list_games"), "fa fa-list", "/game/index.xhtml"));
            model.addElement(new DefaultMenuItem(bundle.getString("link_create_game"), "fa fa-plus-circle", "/game/create.xhtml"));
            model.addElement(new DefaultMenuItem(bundle.getString("link_setup_game"), "fa fa-gear", "/game/setup.xhtml"));
            model.addElement(new DefaultMenuItem(bundle.getString("link_join_game"), "fa fa-step-forward", "/game/join.xhtml"));
            model.addElement(new DefaultMenuItem(bundle.getString("link_control_game"), "fa fa-cogs", "/game/control.xhtml"));
        } catch (MissingResourceException mre) {
            logger.log(Level.WARNING, null, mre);
        }
        menu.setModel(model);
    }

    /**
     *
     * @return
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     *
     * @param menu
     */
    public void setMenu(Menu menu) {
        return;
    }

}
