package cn.kgc.tangcco.zwpl.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseDateUitls {
	private final static String formatString = "yyyy-MM-dd";

	public static String getDateString(Date date) {
		return getDateString(date, formatString);
	}

	public static String getDateString(Date date, String formatString) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.format(date);
	}
	public static Date parse(String source) throws ParseException {
		return parse(source, formatString);
	}
	public static Date parse(String source, String formatString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.parse(source);
	}
}
