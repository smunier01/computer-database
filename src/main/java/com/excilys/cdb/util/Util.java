package com.excilys.cdb.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Util {
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
