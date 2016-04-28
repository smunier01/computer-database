package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

public class LocalDateToTimestamp {

    /**
     * convert a LocalDate to timestamp.
     *
     * @param localDate
     *            LocalDate to convert
     * @return the timestamp
     */
    public static Timestamp convert(final LocalDate localDate) {

        Timestamp timestamp;

        if (localDate == null || localDate.equals(LocalDate.MIN)) {
            timestamp = null;
        } else {
            timestamp = Timestamp.valueOf(localDate.atStartOfDay());
        }

        return timestamp;
    }
}
