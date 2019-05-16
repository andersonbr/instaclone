package br.com.shellcode.instaclone.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
	public static LocalDate toLocalDate(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date localDateToDate(LocalDate dateToConvert) {
		return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date localDateTimeToDate(LocalDateTime dateToConvert) {
		return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date endOfDay(Date date) {
		LocalDateTime eod = toLocalDate(date).atTime(LocalTime.MAX);
		return localDateTimeToDate(eod);
	}

	public static Date startOfDay(Date date) {
		LocalDateTime sod = toLocalDate(date).atTime(LocalTime.MIN);
		return localDateTimeToDate(sod);
	}
}
