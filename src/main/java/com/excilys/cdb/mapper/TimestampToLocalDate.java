package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

public class TimestampToLocalDate {

    /**
     * convert a timestamp to a LocalDate.
     *
     * @param timestamp
     *            timestamp to convert
     * @return the LocalDate
     */
    public static LocalDate convert(final Timestamp timestamp) {

        LocalDate date;

        if (timestamp == null) {
            date = null;
        } else {
            date = timestamp.toLocalDateTime().toLocalDate();
        }

        return date;
    }
}
