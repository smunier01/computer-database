package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class LocalDateMapper {

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
