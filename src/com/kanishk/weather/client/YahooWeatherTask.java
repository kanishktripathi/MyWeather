package com.kanishk.weather.client;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

import com.kanishk.yahoo.pojo.location.Location;
import com.kanishk.yahoo.pojo.weather.Channel;
import com.kanishk.yahoo.pojo.weather.Forecast;
import com.kanishk.yahoo.query.YahooWeatherQuery;


/**
 * The Class YahooWeatherTask. The asynchronous task for performing REST API call 
 * a separate thread from UI. The generic type T could be any UI component which needs to be updated
 * like View, Activity or Fragment.
 */
public class YahooWeatherTask extends AsyncTask<YahooWeatherQuery, Void, Channel> {
	
	/** The listener. */
	private YahooWeatherTaskListener listener;
	
	/** The woe id. */
	private String woeId;
	
	/**
	 * Instantiates a new yahoo weather task.
	 *
	 * @param listener the listener
	 */
	public YahooWeatherTask(YahooWeatherTaskListener listener) {
		this.listener = listener;
	}

	@Override
	protected Channel doInBackground(YahooWeatherQuery... params) {
		Forecast result = null;
		Channel output = null;
		try {
			YahooWeatherQuery query = params[0];
			if(query.isSearchByLocation()) {
				Location place = RestClient.getQueryResult(query.getUrl(), Location.class);
				String woeId = place.getQuery().getResults().getResult().getWoeid();
				YahooWeatherQuery.modifyToPlaceQuery(query, woeId);
			}
			this.woeId = query.getSearchId();
			result = RestClient.getQueryResult(query.getUrl(), Forecast.class);
			output = result.getQuery().getResults().getRss().getChannel();
		} catch (IOException e) {
			Log.e(e.getMessage(), e.getLocalizedMessage());
		} catch (InterruptedException e) {
			Log.e(e.getMessage(), e.getLocalizedMessage());
			return null;
		}
		return output;
	}

	@Override
	protected void onPostExecute(Channel result) {
		listener.postResults(result, woeId);
	}
	
	/**
	 * The listener interface for receiving yahooTask events.
	 * The class that is interested in processing a yahooTask
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addYahooTaskListener<code> method. When
	 * the yahooTask event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see YahooTaskEvent
	 */
	public static interface YahooWeatherTaskListener {
		
		/**
		 * Post results. Method definition to handle the results of Weather updates.
		 * @param weatherChanel the weather chanel after finding the weather results
		 * @param woeid the woeid of the place
		 */
		void postResults(Channel weatherChanel, String woeid);
	}
}
