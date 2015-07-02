package de.hub.cses.ces.entity.text;

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
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "ppt_i18n_text_tbl")
public class I18nText extends BaseEntity {

    @ElementCollection
    @MapKeyColumn(name = "language")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "text")
    @Lob
    @CollectionTable(name = "ppt_i18n_text_localized_tbl", joinColumns = @JoinColumn(name = "fk_i18n_text"))
    private Map<SupportedLanguage, String> texts;

    /**
     *
     */
    public I18nText() {

    }

    /**
     *
     * @param id
     * @param texts
     */
    public I18nText(Long id, Map<SupportedLanguage, String> texts) {
        super(id);
        this.texts = texts;
    }

    /**
     *
     * @return
     */
    public Map<SupportedLanguage, String> getTexts() {
        return texts;
    }

    /**
     *
     * @param texts
     */
    public void setTexts(Map<SupportedLanguage, String> texts) {
        this.texts = texts;
    }

    /**
     *
     * @param language
     * @return
     */
    public String getText(SupportedLanguage language) {
        if (getTexts() != null && getTexts().containsKey(language)) {
            return getTexts().get(language);
        }
        return null;
    }

    /**
     *
     * @param language
     * @param text
     */
    public void addText(SupportedLanguage language, String text) {
        if (texts == null) {
            texts = new HashMap<>();
        }
        texts.put(language, text);
    }

}
