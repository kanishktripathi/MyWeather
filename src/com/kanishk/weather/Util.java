package com.kanishk.weather;

import java.util.regex.Pattern;

/**
 * The Class Util. A class for utility methods
 */
public class Util {
	
	/** The Constant AM. */
	private static final String AM = "am";
	
	/** The Constant PM. */
	private static final String PM = "pm";
	
	/** The pattern. */
	private static Pattern PATTERN = Pattern.compile(" ");
	
	/**
	 * Checks if is day. This method splits the strings and computes time based on the string values.
	 * Since the Yahoo API we used does not provide the field for current time
	 * we are forced to split strings from last build and find if its day or not by writing custom
	 * methods for comparing with sunrise and sunset
	 * @param currentTime the current time
	 * @param sunrise the sunrise time
	 * @param sunset the sunset time
	 * @return true, if is day
	 */
	public static boolean isDay(String currentTime, String sunrise, String sunset) {
		String[] current = PATTERN.split(currentTime);
		int index = current.length - 1;
		String[] sunriseArr = PATTERN.split(sunrise);
		String[] sunsetArr = PATTERN.split(sunset);
		boolean pastSunrise = isGreaterTime(current[index - 2], current[index - 1], 
				sunriseArr[0], sunriseArr[1]);
		if(pastSunrise) {
			boolean isPastSunset = isGreaterTime(current[index - 2], current[index - 1], 
					sunsetArr[0], sunsetArr[1]);
			return !isPastSunset;
		}
		return false;
	}
	
	
	/**
	 * Checks if the string time is greater than the compared time. For example time value s 11:40
	 * and amPm is pm.the comparison time is 5:30 and compareAmPm is pm. So it returns true.
	 * @param time the time value
	 * @param amPm whether time is in am or pm
	 * @param comparedTime the compared time value
	 * @param compareAmPm if the time to compare is in am or pm
	 * @return true, if time is greater than the comparedTime
	 */
	private static boolean isGreaterTime(String time, String amPm ,  String comparedTime, 
			String compareAmPm) {
		boolean retVal;
		if(amPm.equals(compareAmPm)) {
			if(comparedTime.length() != time.length()) {
				retVal = comparedTime.length() < time.length();
			} else {
				retVal = time.compareTo(comparedTime) > 0;
			} 
		} else if(amPm.equalsIgnoreCase(AM) && compareAmPm.equalsIgnoreCase(PM)) {
			retVal = false;
		} else {
			retVal = true;
		}
		return retVal;
	}
}
