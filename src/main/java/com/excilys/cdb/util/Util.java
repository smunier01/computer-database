package com.excilys.cdb.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * get integer from a HttpServletRequest object.
     *
     * @param request
     *            the request
     * @param key
     *            key where the value should be
     * @param def
     *            default value in case the integer doesn't exist
     * @return int retrieved
     */
    public static int getInt(final HttpServletRequest request, final String key, final int def) {
        final String str = request.getParameter(key);

        int result;

        if (str == null) {
            result = def;
        } else {
            try {
                result = Integer.parseInt(str);
            } catch (final NumberFormatException e) {
                result = def;
            }
        }

        return result;
    }
}
