package com.kanishk.yahoo.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.kanishk.constants.Constants;

/**
 * The Class YahooWeatherQuery. Used for preparing query URL for the Yahoo REST call.
 */
public class YahooWeatherQuery {
	
	private static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?";
	
	/** The search by lat long. */
	private Type queryType;
	
	/** The search id. */
	private String searchValue;
	
	/** The temperature. */
	private String temperature;
	
	/**
	 * Instantiates a new yahoo weather query.
	 * @param searchId the search id
	 */
	private YahooWeatherQuery(String searchId) {
		super();
		this.searchValue = searchId;
		this.temperature = Constants.TEMP_CELCIUS;
	}	
	
	/**
	 * Instantiates a new yahoo weather query.
	 *
	 * @param queryType the query type
	 
	 * @param searchValue the search value
	 * @param temperature the temperature
	 */
	public YahooWeatherQuery(Type queryType, String searchValue, String temperature) {
		super();
		this.queryType = queryType;
		this.searchValue = searchValue;
		this.temperature = temperature;
	}


	/**
	 * Checks if is search by using the geo location. The lat long are in the form
	 * latitude,longitude
	 *
	 * @return true, if is search by location
	 */
	public boolean isSearchByLocation() {
		return Type.LAT_LONG.equals(queryType);
	}

	/**
	 * Gets the search query by lat long.
	 * @param searchId the search id
	 * @return the search query by lat long
	 */
	public static YahooWeatherQuery getSearchQueryByLatLong(double lat, double longitude) {
		StringBuilder sb = new StringBuilder();
		sb.append(lat).append(Constants.COMMA).append(longitude);
		YahooWeatherQuery query = new YahooWeatherQuery(sb.toString());
		query.queryType = Type.LAT_LONG;
		return query;
	}
	
	/**
	 * Gets the search query for location search by text.
	 * Example: Find places that start with 'Las Veg'
	 *
	 
	 * @param searchText the search text
	 * @return the search query by lat long
	 */
	public static YahooWeatherQuery getTextSearchQuery(String searchText) {
		YahooWeatherQuery query = new YahooWeatherQuery(searchText);
		query.queryType = Type.SEARCH;
		return query;
	}

	/**
	 * Gets the search query for location.
	 *
	 
	 * @param locationId the location id
	 * @param temperature the temperature
	 * @return the search query for location
	 */
	public static YahooWeatherQuery getSearchQueryForLocation(String locationId, 
			String temperature) {
		YahooWeatherQuery query = new YahooWeatherQuery(Type.WEATHER, locationId, temperature);
		query.queryType = Type.WEATHER;
		return query;
	}
	
	/**
	 * Gets the search query for location.
	 *
	 * @param locationId the location id
	 * @return the search query for location
	 */
	public static YahooWeatherQuery getWeatherQuery(String locationId, String temperature) {
		return getSearchQueryForLocation(locationId, temperature);
	}
	
	/**
	 * Modifies the the query to search for weather. Changes the searchId value to woeId
	 * and query type to weather
	 * @param query the query whose parameters are to be modified.
	 * @param woeId the location id
	 */
	public static void modifyToPlaceQuery(YahooWeatherQuery query, String woeId) {
		query.queryType = Type.WEATHER;
		query.searchValue = woeId;
	}
	
	/**
	 * Gets the final url with query parameters.
	 *
	 * @return the final url
	 * @throws UnsupportedEncodingException 
	 */
	public String getUrl() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(BASE_URL);
		if(Type.LAT_LONG.equals(queryType)) {
			sb.append("q=select%20woeid%20from%20geo.placefinder%20where%20text%3D%22").append(searchValue)
			.append("%22%20and%20gflags%3D%22R%22&format=json");
		} else if(Type.SEARCH.equals(queryType)) {
			searchValue = URLEncoder.encode(searchValue, Constants.UTF8);
			sb.append("q=select%20woeid,name,country,admin1%20from%20geo.places%20where%20placeTypeName.code=7%20and%20text%20=%27")
			.append(searchValue).append("*%27&format=json");
		} else {
			sb.append("q=select%20%2a%20from%20xml%20where%20url%3D%27http%3A%2F%2Fweather.yahooapis.com%2Fforecastrss%3Fw%3D")
			.append(searchValue).append("%26u%3D").append(temperature).append("%27&format=json");
		}
		return sb.toString();
	}

	/**
	 * Sets the temperature.
	 *
	 * @param temperature the new temperature
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	/**
	 * Gets the search id for the query object.
	 *
	 * @return the search id
	 */
	public String getSearchId() {
		return this.searchValue;
	}
	
	/**
	 * The Enum Type. The type of query to hit.
	 */
	private enum Type {
		
		/** The lat long. Find location by coordinates*/
		LAT_LONG, 
		/** Type for getting weather. The search value must be woeId.*/
		WEATHER, 
		/** The search. Search for location by text */
		SEARCH;
	};
}
