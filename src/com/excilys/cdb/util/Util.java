package com.excilys.cdb.util;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Util {
	public static LocalDate timestampToLocalDate(Timestamp timestamp) {
		
		LocalDate date;
		
		if (timestamp == null) {
			date = null;
		} else {
			date = timestamp.toLocalDateTime().toLocalDate();
		}
		
		return date;
	}
}
