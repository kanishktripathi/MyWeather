package com.kanishk.weather.adapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.kanishk.weather.client.RestClient;

/**
 * The Class BaseSearchProvider. This is a generic search provider.
 *
 * @param <SearchClass> the generic type for the class whose list will be used as search result
 * @param <Response> the generic type for the response class from which the list will be obtained. 
 */
public abstract class BaseSearchProvider<SearchClass, Response> {
	
	/** The adapter for the search provider. */
	private SearchAdapter<SearchClass> adapter;
	
	/** The class p. */
	private final Class<Response> classP;
	
	/**
	 * Gets the response list.
	 *
	 * @param result the result
	 * @return the response list
	 */
	public abstract List<SearchClass> getResponseList(Response result);
	
	/**
	 * Gets the text.
	 *
	 * @param item the item
	 * @return the text
	 */
	public abstract String getText(SearchClass item);
	
	
	/**
	 * Gets the query url from the derived class.
	 * @param searchValue the search value for the url
	 * @return the query url
	 * @throws UnsupportedEncodingException 
	 */
	public abstract String getQueryUrl(String searchValue) throws UnsupportedEncodingException;

	/**
	 * Instantiates a new base search provider.
	 *
	 * @param context the context
	 * @param resource the resource
	 * @param baseUrl the base url
	 * @param classVar the class var
	 */
	protected BaseSearchProvider(Context context, int resource, Class<Response> classVar) {
		this.classP = classVar;
		adapter = new SearchAdapter<SearchClass>(context, resource) {

			@Override
			public List<SearchClass> getResultList(String searchValue) {
				List<SearchClass> returnList = null;
				try {
					Response result = RestClient.getQueryResult(getQueryUrl(searchValue), classP);
					returnList = getResponseList(result);
				} catch (IOException e) {
					returnList = new ArrayList<SearchClass>();
					Log.e(e.toString(), e.getMessage());
				} catch (InterruptedException e) {
					returnList = new ArrayList<SearchClass>();
					Log.e(e.toString(), e.getMessage());
				}
				return returnList;
			}

			@Override
			public String getViewText(int position) {
				return getText(adapter.getItem(position));
			}
		};
	}

	/**
	 * Gets the search adapter set by the searh provider.
	 *
	 * @return the adapter
	 */
	public SearchAdapter<SearchClass> getAdapter() {
		return adapter;
	}
}
