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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Named("CalendarUtil")
@RequestScoped
public class CalendarUtil {

    private static final String[] LOCALE_IDENTIFIERS = new String[]{"de", "DE"};
    private static final String TIME_ZONE_IDENTIFIER = "Europe/Berlin";

    /**
     *
     * @param date
     * @return
     */
    public Calendar getCalendar(Date date) {
        Locale locale = new Locale(LOCALE_IDENTIFIERS[0], LOCALE_IDENTIFIERS[1]);
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE_IDENTIFIER);
        Calendar cal = GregorianCalendar.getInstance(tz, locale);
        cal.setTime(date);
        return cal;
    }

    /**
     *
     * @param date
     * @return
     */
    public Date getFirstDayOfMonth(Date date) {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date firstDayOfMonth = cal.getTime();
        return firstDayOfMonth;
    }

    /**
     *
     * @param date
     * @return
     */
    public Date getFirstDayOfYear(Date date) {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        Date firstDayOfYear = cal.getTime();
        return firstDayOfYear;
    }

    /**
     *
     * @param date
     * @return
     */
    public int getNumberOfDaysInCurrentMonth(Date date) {
        Calendar cal = getCalendar(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     *
     * @return
     */
    public String getTimeZoneIdentifier() {
        return TIME_ZONE_IDENTIFIER;
    }

}
