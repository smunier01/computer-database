package com.excilys.cdb.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * static methods used by multiple packages.
 *
 * @author simon
 */
public final class Util {

    /**
     *
     */
    private Util() {
    }

    /**
     * Convert a string to a LocalDate.
     *
     * @param s
     *            string to convert to LocalDate
     * @return date
     */
    public static LocalDate stringToLocalDate(final String s) {
        LocalDate date = null;

        try {
            date = LocalDate.parse(s);
        } catch (final DateTimeParseException e) {
            date = LocalDate.MIN;
        }

        return date;
    }
}
