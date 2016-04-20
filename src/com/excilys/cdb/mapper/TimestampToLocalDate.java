package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

public class TimestampToLocalDate {
	
	private TimestampToLocalDate() {}
	
	public static LocalDate convert(Timestamp timestamp) {
		
		LocalDate date;
		
		if (timestamp == null) {
			date = null;
		} else {
			date = timestamp.toLocalDateTime().toLocalDate();
		}
		
		return date;
	}
}
