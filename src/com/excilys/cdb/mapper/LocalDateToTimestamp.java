package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

public class LocalDateToTimestamp {

	private LocalDateToTimestamp() {
	}

	public static Timestamp convert(LocalDate localDate) {

		Timestamp timestamp;
		
		if (localDate.equals(LocalDate.MIN)) {
			timestamp = null;
		} else {
			timestamp = Timestamp.valueOf(localDate.atStartOfDay());
		}
		
		return timestamp;
	}
}
