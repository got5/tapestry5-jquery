package org.got5.tapestry5.jquery.data;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formats {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public static Date parseDate(String strDate) {
		Date date = null;
		try {
			date = simpleDateFormat.parse(strDate);
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
		return date;
	}

	public static Format getDateFormat() {
		return simpleDateFormat;
	}
}
