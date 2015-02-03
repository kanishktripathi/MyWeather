package com.kanishk.weather.adapter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import android.content.Context;

import com.kanishk.constants.Constants;
import com.kanishk.yahoo.pojo.places.Admin1;
import com.kanishk.yahoo.pojo.places.Place;
import com.kanishk.yahoo.pojo.places.Places;
import com.kanishk.yahoo.query.YahooWeatherQuery;

/**
 * The Class YahooSearchProvider. This class has been designed for getting places list using Yahoo
 * geoplaces. The class Place contains data for a place(woeid, name, country).
 * The Places class is the response from the REST client and we obtain the list of places from it.
 */
public class YahooSearchProvider extends BaseSearchProvider<Place, Places> {
	
	
	/** The query. */
	private YahooWeatherQuery query;
	
	/**
	 * Instantiates a new yahoo search provider.
	 *
	 * @param context the context
	 * @param resource the resource
	 * @param baseUrl the base url
	 */
	public YahooSearchProvider(Context context, int resource) {
		super(context, resource, Places.class);
		this.query = YahooWeatherQuery.getTextSearchQuery(null);
	}

	@Override
	public List<Place> getResponseList(Places result) {
		return result.getQuery().getResults().getPlace();
	}

	@Override
	public String getText(Place item) {
		StringBuilder sb = new StringBuilder();
		sb.append(item.getName()).append(Constants.COMMA);
		Admin1 admin = item.getAdmin1();
		if(admin != null && admin.getCode().length() > 0) {
			sb.append(admin.getCode()).append(Constants.COMMA);
		}
		sb.append(item.getCountry().getContent());
		return sb.toString();
	}

	@Override
	public String getQueryUrl(String searchValue) throws UnsupportedEncodingException {
		query.setSearchValue(searchValue);
		return query.getUrl();
	}

}
