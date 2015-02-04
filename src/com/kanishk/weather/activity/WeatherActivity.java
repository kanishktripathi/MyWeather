package com.kanishk.weather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.kanishk.constants.Constants;
import com.kanishk.constants.Temperature;
import com.kanishk.weather.R;
import com.kanishk.weather.client.YahooWeatherTask;
import com.kanishk.yahoo.pojo.weather.Channel;
import com.kanishk.yahoo.query.YahooWeatherQuery;

/**
 * The Class WeatherActivity. The class for maintaining the weather information.
 */
public class WeatherActivity extends BaseSearchActivity implements OnClickListener {

	/** The update container. */
	private WeatherUpdateContainer updateContainer;

	/** The temperature unit. */
	private Button temperatureUnit;
	
	/** The weather activity container view. */
	private View weatherActivityContainerView;
	
	/** The weather finding task currently executing. It */
	private YahooWeatherTask currentWeatherTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		setupAutoSuggest();
		updateContainer = WeatherUpdateContainer.getInitUpdateContainer(this);
		setUpOptionButtons();
		weatherActivityContainerView = findViewById(R.id.weather_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onClick(View v) {
		Intent i = new Intent();
        i.setClass(this, FavouriteActivity.class);
        startActivity(i);
	}
	
	/**
	 * Sets the up temperature button.
	 */
	private void setUpOptionButtons() {
		this.temperatureUnit = (Button) findViewById(R.id.tempr_unit);
		temperatureUnit.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				changeTemperature();
			}
		});
		ImageButton favourite = (ImageButton) findViewById(R.id.favourites);
		favourite.setOnClickListener(this);
	}

	protected void processWeatherQueryResponse(Channel response) {
		if (response != null) {
			updateContainer.updateElements(response);
			weatherActivityContainerView.setVisibility(View.VISIBLE);
		} else {
			displayMessage(getString(R.string.weather_error));
		}
	}
	
	private void changeTemperature() {
		SharedPreferences pref = getSharedPreferences();
		String tempUnit = pref.getString(Constants.TEMPERATURE, Temperature.CELCIUS.getValue());
		Temperature temperature;
		if(Temperature.CELCIUS.getValue().equals(tempUnit)) {
			tempUnit = getString(R.string.degreeF);
			temperature = Temperature.FARENHEIT;
		} else {
			tempUnit = getString(R.string.degreeC);
			temperature = Temperature.CELCIUS;
		}
		Editor edit = pref.edit();
		temperatureUnit.setText(tempUnit);
		edit.putString(Constants.TEMPERATURE, temperature.getValue());
		edit.commit();
		updateContainer.updateTemperature(temperature, tempUnit);		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		updateContainer = null;
		if(this.currentWeatherTask != null) {
			currentWeatherTask.cancel(true);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refresh();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(this.currentWeatherTask != null) {
			currentWeatherTask.cancel(true);
		}
	}

	/**
	 * Search weather.
	 * 
	 * @param query weather query
	 *            the query
	 */
	protected void searchWeather(YahooWeatherQuery query) {
		if (hasNetAccess()) {
			YahooWeatherTask weatherTask = new YahooWeatherTask(this);
			this.currentWeatherTask = weatherTask;
			weatherTask.execute(query);
		} else {
			displayMessage(getString(R.string.net_connect_error));
		}
	}
	
	@Override
	public void postResults(Channel weatherChanel, String woeid) {
		//Add the city to shared preferences for persistence
		this.currentWeatherTask = null;
		processWeatherQueryResponse(weatherChanel);
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(Constants.WOEID, woeid);
		editor.commit();
	}

}
