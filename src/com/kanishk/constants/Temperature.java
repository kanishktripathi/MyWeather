package com.kanishk.constants;


/**
 * The Enum Temperature. Constant for storing temperature type
 */
public enum Temperature {

	/** The celcius. */
	CELCIUS("c"),
	/** The farenheit. */
	FARENHEIT("f");
	
	/** The val. */
	private final String val;
	
	/**
	 * Instantiates a new temperature.
	 *
	 * @param c the c
	 */
	private Temperature(String c) {
		val = c;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return val;
	}
}
