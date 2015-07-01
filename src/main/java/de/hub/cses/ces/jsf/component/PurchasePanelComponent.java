package de.hub.cses.ces.jsf.component;

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
import de.hub.cses.ces.entity.economy.DynamicSupply;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@FacesComponent("PurchasePanelComponent")
public class PurchasePanelComponent extends UINamingContainer {

    /**
     *
     */
    public PurchasePanelComponent() {
        super();
    }

    /**
     *
     * @param ae
     */
    public void actionListener(ActionEvent ae) {
    }

    private DynamicSupply getSelectedSupply() {
        return (DynamicSupply) getAttributes().get("selectedSupply");
    }

    private int getQuantity() {
        return (int) getAttributes().get("quantity");
    }

    /**
     *
     * @return
     */
    public int getMin() {
        return 0;
    }

    /**
     *
     * @return
     */
    public int getMax() {
        DynamicSupply selectedSupply;
        return ((selectedSupply = getSelectedSupply()) != null) ? selectedSupply.getAvailableQuantity() : 0;
    }

    /**
     *
     * @return
     */
    public double getCosts() {
        DynamicSupply selectedSupply;
        return ((selectedSupply = getSelectedSupply()) != null) ? ((double) getQuantity() * selectedSupply.getPricePerUnit()) : 0;
    }

    /**
     *
     * @param i
     * @return
     */
    public String getStyleClass(int i) {
        if (i == 1) {
            return "fa fa-arrow-circle-up";
        } else if (i == -1) {
            return "fa fa-arrow-circle-down";
        }
        return "";
    }

}
