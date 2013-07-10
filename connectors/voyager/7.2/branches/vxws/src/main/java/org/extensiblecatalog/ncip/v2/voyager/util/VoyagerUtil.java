package org.extensiblecatalog.ncip.v2.voyager.util;

import java.sql.Date;
import java.util.GregorianCalendar;

/**
 * Utility methods
 * 
 * @author SharmilaR
 *
 */
public class VoyagerUtil {

	/**
	 * Converts Date to Gregorian calendar
	 * 
	 * @param date
	 * @return
	 */
	public static GregorianCalendar convertDateToGregorianCalendar(Date date) {
		if (date != null) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			
			return gc;
		} else {
			return null;
		}
	}
}
