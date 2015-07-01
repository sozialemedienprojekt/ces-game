/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hub.cses.ces.entity.validator;

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
import de.hub.cses.ces.entity.game.Game;
import de.hub.cses.ces.entity.validator.qualifier.ValidateDateRange;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
public class DateRangeValidator implements ConstraintValidator<ValidateDateRange, Game> {

    private String start;
    private String end;

    /**
     *
     * @param validateDateRange
     */
    @Override
    public void initialize(ValidateDateRange validateDateRange) {
        start = validateDateRange.startAt();
        end = validateDateRange.stopAt();
    }

    /**
     *
     * @param game
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Game game, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Date startDate = game.getStartAt();
            Date endDate = game.getStopAt();
            return (startDate.before(endDate) || startDate.equals(endDate));
        } catch (Throwable e) {
            System.err.println(e);
        }

        return false;
    }

}
