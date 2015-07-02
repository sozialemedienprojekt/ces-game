package de.hub.cses.ces.util;

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
import de.hub.cses.ces.entity.text.I18nText;
import de.hub.cses.ces.entity.text.SupportedLanguage;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("I18nTextUtil")
@RequestScoped
public class I18nTextUtil {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private transient Logger logger;

    /**
     *
     * @param text
     * @return
     */
    public String get(I18nText text) {
        if (text == null) {
            return "text could not be found";
        }
        String str = null;
        try {
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            SupportedLanguage language = SupportedLanguage.fromLocale(locale.toString());
            str = text.getText(language);
        } catch (NullPointerException npe) {
            logger.log(Level.INFO, null, npe);
        }
        return str;
    }

    /**
     *
     * @param message
     * @return
     */
    public I18nText create(Map<SupportedLanguage, String> message) {
        I18nText text = null;
        if (message != null && !message.isEmpty()) {
            text = new I18nText();
            for (SupportedLanguage language : message.keySet()) {
                text.addText(language, message.get(language));
            }
        }
        return text;
    }
}
