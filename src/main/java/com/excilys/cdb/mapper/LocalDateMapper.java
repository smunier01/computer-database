package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

public enum LocalDateMapper {
    INSTANCE;

    public static LocalDateMapper getInstance() {
        return INSTANCE;
    }

    public LocalDate fromTimestamp(Timestamp timestamp) {
        LocalDate date;

        if (timestamp == null) {
            date = null;
        } else {
            date = timestamp.toLocalDateTime().toLocalDate();
        }

        return date;
    }

    public Timestamp toTimestamp(LocalDate localDate) {

        Timestamp timestamp;

        if ((localDate == null) || localDate.equals(LocalDate.MIN)) {
            timestamp = null;
        } else {
            timestamp = Timestamp.valueOf(localDate.atStartOfDay());
        }

        return timestamp;

    }
}
