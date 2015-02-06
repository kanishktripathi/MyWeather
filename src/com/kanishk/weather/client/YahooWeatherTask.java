package com.kanishk.weather.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.kanishk.yahoo.pojo.location.Location;
import com.kanishk.yahoo.pojo.weather.Astronomy;
import com.kanishk.yahoo.pojo.weather.Atmosphere;
import com.kanishk.yahoo.pojo.weather.Channel;
import com.kanishk.yahoo.pojo.weather.Condition;
import com.kanishk.yahoo.pojo.weather.Forecast;
import com.kanishk.yahoo.pojo.weather.Forecast_;
import com.kanishk.yahoo.pojo.weather.Item;
import com.kanishk.yahoo.pojo.weather.Wind;
import com.kanishk.yahoo.query.YahooWeatherQuery;

/**
 * The Class YahooWeatherTask. The asynchronous task for performing REST API
 * call a separate thread from UI. This is designed for the Yahoo weather API
 * objects.
 */
public class YahooWeatherTask extends
		AsyncTask<YahooWeatherQuery, Void, Channel> {

	/** The listener. */
	private YahooWeatherTaskListener listener;

	/** The woe id. */
	private String woeId;

	/**
	 * Instantiates a new yahoo weather task.
	 * 
	 * @param listener
	 *            the listener
	 */
	public YahooWeatherTask(YahooWeatherTaskListener listener) {
		this.listener = listener;
	}

	@Override
	protected Channel doInBackground(YahooWeatherQuery... params) {
		Channel output = null;
		try {
			YahooWeatherQuery query = params[0];
			if (query.isSearchByLocation()) {
				Location place = RestClient.getQueryResult(query.getUrl(),
						Location.class);
				String woeId = place.getQuery().getResults().getResult()
						.getWoeid();
				YahooWeatherQuery.modifyToPlaceQuery(query, woeId);
			}
			this.woeId = query.getSearchId();
			/*
			 * The commented lines will be removed. Its an alternative to use
			 * Gson library for JSON parsing if the performance issues do not
			 * come with Gson
			 */
			// JSONObject jsonObject =
			// RestClient.getQueryResult(query.getUrl());
			// output = createChannel(jsonObject);
			Forecast result = RestClient.getQueryResult(query.getUrl(),
					Forecast.class);
			if (result != null) {
				output = result.getQuery().getResults().getRss().getChannel();
			}
		} catch (IOException e) {
			Log.e(e.getMessage(), e.getLocalizedMessage());
		} catch (InterruptedException e) {
			Log.e(e.getMessage(), e.getLocalizedMessage());
		}/*
		 * catch (JSONException e) { Log.e(e.getMessage(),
		 * e.getLocalizedMessage()); return null; }
		 */
		return output;
	}

	@Override
	protected void onPostExecute(Channel result) {
		listener.postResults(result, woeId);
	}

	/**
	 * Manually traverses the JSONObject returned from the weather query and
	 * parses into Channel object. This has been done to improve performance
	 * over the gson library parsing. The parsing time is not consistent on
	 * repeated use of Gson. This is done if we encounter slow parsing from Gson
	 * library. This method will be deprecated in future if the performance of
	 * Gson is good on subsequent tests.
	 * 
	 * @param obj
	 *            the JSONObject
	 * @return the channel object of Yahoo weather
	 * @throws JSONException
	 *             the JSON exception
	 */
	@SuppressWarnings("unused")
	private static Channel createChannel(JSONObject obj) throws JSONException {
		JSONObject chanJson = obj.getJSONObject("query")
				.getJSONObject("results").getJSONObject("rss")
				.getJSONObject("channel");
		Astronomy astronomy = new Astronomy();
		JSONObject astroJs = chanJson.getJSONObject("astronomy");
		astronomy.setSunrise(astroJs.getString("sunrise"));
		astronomy.setSunset(astroJs.getString("sunset"));
		JSONObject atmosp = chanJson.getJSONObject("atmosphere");
		JSONObject windJs = chanJson.getJSONObject("wind");
		JSONObject locJs = chanJson.getJSONObject("location");
		JSONObject condJs = chanJson.getJSONObject("item").getJSONObject(
				"condition");
		Wind wind = new Wind();
		wind.setChill(windJs.getString("chill"));
		wind.setSpeed(windJs.getString("speed"));
		Atmosphere atmosphere = new Atmosphere();
		atmosphere.setHumidity(atmosp.getString("humidity"));
		atmosphere.setPressure(atmosp.getString("pressure"));
		Condition condition = new Condition();
		condition.setCode(condJs.getString("code"));
		condition.setTemp(condJs.getString("temp"));
		condition.setText(condJs.getString("text"));
		com.kanishk.yahoo.pojo.weather.Location location = new com.kanishk.yahoo.pojo.weather.Location();
		location.setCity(locJs.getString("city"));
		location.setCountry(locJs.getString("country"));
		JSONArray forecastAyy = chanJson.getJSONObject("item").getJSONArray(
				"forecast");
		int len = forecastAyy.length();
		List<Forecast_> forecastList = new ArrayList<Forecast_>(len);
		for (int i = 0; i < len; i++) {
			Forecast_ forecast = returnForecast((JSONObject) forecastAyy.get(i));
			forecastList.add(forecast);
		}
		Item item = new Item();
		item.setForecast(forecastList);
		item.setCondition(condition);
		Channel channel = new Channel();
		channel.setItem(item);
		channel.setAstronomy(astronomy);
		channel.setWind(wind);
		channel.setAtmosphere(atmosphere);
		channel.setLocation(location);

		channel.setLastBuildDate(chanJson.getString("lastBuildDate"));
		return channel;
	}

	/**
	 * Return forecast.
	 * 
	 * @param json
	 *            the json
	 * @return the forecast_
	 * @throws JSONException
	 *             the JSON exception
	 */
	private static Forecast_ returnForecast(JSONObject json)
			throws JSONException {
		Forecast_ foreCast = new Forecast_();
		foreCast.setCode(json.getString("code"));
		foreCast.setDay(json.getString("day"));
		foreCast.setHigh(json.getString("high"));
		foreCast.setLow(json.getString("low"));
		foreCast.setText(json.getString("text"));
		return foreCast;
	}

	/**
	 * The listener interface for receiving yahooTask events. The class that is
	 * interested in processing a yahooTask event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addYahooTaskListener<code> method. When
	 * the yahooTask event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see YahooTaskEvent
	 */
	public static interface YahooWeatherTaskListener {

		/**
		 * Post results. Method definition to handle the results of Weather
		 * updates.
		 * 
		 * @param weatherChanel
		 *            the weather chanel after finding the weather results
		 * @param woeid
		 *            the woeid of the place
		 */
		void postResults(Channel weatherChanel, String woeid);
	}

}
