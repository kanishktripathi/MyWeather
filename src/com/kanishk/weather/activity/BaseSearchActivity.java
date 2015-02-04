package com.kanishk.weather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.kanishk.constants.Constants;
import com.kanishk.constants.Temperature;
import com.kanishk.weather.R;
import com.kanishk.weather.adapter.YahooSearchProvider;
import com.kanishk.weather.client.RestClient;
import com.kanishk.weather.client.YahooWeatherTask;
import com.kanishk.weather.client.YahooWeatherTask.YahooWeatherTaskListener;
import com.kanishk.yahoo.pojo.places.Place;
import com.kanishk.yahoo.query.YahooWeatherQuery;

/**
 * The Class BaseSearchActivity. This is the base class to perform weather
 * related search. It contains methods for both searching in the text box as
 * well as searches by users current location.
 */
public abstract class BaseSearchActivity extends Activity implements LocationListener, YahooWeatherTaskListener {

	/** The location. */
	private AutoCompleteTextView location;
	
	private View dummyView;

	/** The adapter. */
	private YahooSearchProvider adapter;

	/** The location track. */
	private LocationTracker locationTrack;
	
	/** The shared preference object for the activities*/
	private SharedPreferences preferences;
	
	/** The constant for shared preference name*/
	private static final String SHARED_ACTIVITY = "com.kanishk.weather.BaseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.preferences = getSharedPreferences(SHARED_ACTIVITY, MODE_PRIVATE);
		this.locationTrack = new LocationTracker(this,
				(LocationManager) getSystemService(LOCATION_SERVICE));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_layout, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_location:
			locationSearch();
			return true;
		case R.id.action_refresh:
			refresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Setup auto suggest. Use this method to set up and enable auto suggest in
	 * your derived activity. It will be preferable to call it on the onCreate
	 * method of the derived activity.
	 */
	protected void setupAutoSuggest() {
		this.location = (AutoCompleteTextView) findViewById(R.id.autoSuggest);
		this.adapter = new YahooSearchProvider(this, R.layout.list_layout);
		this.dummyView = findViewById(R.id.dummyView);
		location.setAdapter(this.adapter.getAdapter());
		location.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Place place = (Place) parent.getItemAtPosition(position);
				dummyView.requestFocus();
				location.setText(Constants.EMPTY);
				autoSuggestClickAction(place);
			}
		});
		//Listener to hide keyboard
		location.setOnFocusChangeListener(new OnFocusChangeListener() {			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus) {
					InputMethodManager inputMethodManager =(InputMethodManager)getSystemService
							(Activity.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				}
			}
		});
	}

	/**
	 * Auto suggest click action. The action to peform when a suggestion in
	 * location auto suggest is selected. Override this method to perform the
	 * required action in derived activity.
	 * 
	 * @param place
	 *            the place
	 */
	protected void autoSuggestClickAction(Place place) {
		YahooWeatherQuery query = YahooWeatherQuery.getSearchQueryForLocation(place.getWoeid(), 
				preferences.getString(Constants.TEMPERATURE, Temperature.CELCIUS.getValue()));
		searchWeather(query);
	}

	/**
	 * Gets the weather by lat long.
	 * 
	 * @param location
	 *            the location
	 * @return the weather by lat long
	 */
	private void getWeatherByLatLong(Location location) {
		StringBuilder sb = new StringBuilder();
		sb.append(location.getLatitude()).append(Constants.COMMA)
				.append(location.getLongitude());
		String temperature = preferences.getString(Constants.TEMPERATURE, Temperature.CELCIUS.getValue());
		YahooWeatherQuery query = YahooWeatherQuery.getSearchQueryByLatLong(
				location.getLatitude(), location.getLongitude(), temperature);
		searchWeather(query);
	}

	/**
	 * Search weather. Implement this method in your derived class to 
	 * find the weather information from the given {@link YahooWeatherQuery}.
	 * Use {@link YahooWeatherTask} to perform weather task. Keep this method
	 * blank in the derived activity for not finding any weather information. 
	 * @param query weather query.
	 *            the query
	 */
	protected abstract void searchWeather(YahooWeatherQuery query);

	/**
	 * The method to again find weather for the currently searched location on
	 * click of refresh option from the action bar. Override this in your
	 * activity for the actions on the button
	 */
	protected void refresh() {
		if(preferences == null) {
			return;
		}
		String woeid = preferences.getString(Constants.WOEID, null);
		if (woeid != null) {
			String temperature = preferences.getString(Constants.TEMPERATURE,
					Temperature.CELCIUS.getValue());
			YahooWeatherQuery query = YahooWeatherQuery.getSearchQueryForLocation(woeid, temperature);
			this.searchWeather(query);
		} else {
			locationSearch();
		}
	}

	protected SharedPreferences getSharedPreferences() {
		return this.preferences;
	}

	/**
	 * Location search. Searched for the user's current location. This method is
	 * triggered on click of the action bars location button.
	 */
	protected void locationSearch() {
		if (locationTrack.isLocationServiceAvailable()) {
			locationTrack.findCurrentLocation(this);
		} else {
			displayMessage(getString(R.string.loc_error));
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		locationTrack.removeListener(this);
		getWeatherByLatLong(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	protected boolean hasNetAccess() {
		return RestClient.hasNetwork((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
	}

	protected void displayMessage(String text) {
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}

	/**
	 * The Class LocationTracker. This is a location tracker class that find the
	 * current location of the user. It only searches for a coarse location by
	 * default as weather is for a whole city and doesn't need precise user
	 * location.
	 */
	private static class LocationTracker {

		/** The location manager. */
		private LocationManager locationManager;

		/** The default provider criteria. */
		private Criteria defaultProviderCriteria;

		/**
		 * Instantiates a new location tracker.
		 * 
		 * @param applicationContext
		 *            the application context
		 * @param locationManager
		 *            the location manager
		 */
		public LocationTracker(Context applicationContext,
				LocationManager locationManager) {
			this.locationManager = locationManager;
			setDefaultCriteria();

		}

		/**
		 * Removes the listener.
		 * 
		 * @param listener
		 *            the listener
		 */
		public void removeListener(LocationListener listener) {
			this.locationManager.removeUpdates(listener);
		}

		/**
		 * Checks if is location service available.
		 * 
		 * @return true, if is location service available
		 */
		public boolean isLocationServiceAvailable() {
			return locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)
					|| locationManager
							.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		}

		/**
		 * Find current location. A single update has been used as the current
		 * design does not require the app to constantly update the user
		 * location.
		 * 
		 * @param listener
		 *            the listener
		 */
		public void findCurrentLocation(LocationListener listener) {
			locationManager.requestSingleUpdate(defaultProviderCriteria,
					listener, null);
		}

		/**
		 * Sets the default criteria for location search.
		 */
		private void setDefaultCriteria() {
			Criteria locCriteria = new Criteria();
			locCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
			locCriteria.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
			locCriteria.setAltitudeRequired(false);
			this.defaultProviderCriteria = locCriteria;
		}
	}
}
