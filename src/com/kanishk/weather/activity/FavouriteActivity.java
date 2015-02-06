package com.kanishk.weather.activity;

import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kanishk.constants.Constants;
import com.kanishk.weather.R;
import com.kanishk.weather.activity.CustomFavouriteContainer.FavouriteLayoutListener;
import com.kanishk.yahoo.pojo.places.Place;
import com.kanishk.yahoo.pojo.weather.Channel;
import com.kanishk.yahoo.query.YahooWeatherQuery;

/**
 * The Class FavouriteActivity. The class for managing the favorite places to add from which
 * the user can navigate to. Currently upto 10 favorite places can be added
 */
public class FavouriteActivity extends BaseSearchActivity implements FavouriteLayoutListener {
	
	/** The Constant MAX_CAPACITY. */
	private static final int MAX_CAPACITY = 10;
	
	/** The layout list. */
	private LinearLayout layoutList;
	
	/** The inflator. */
	private LayoutInflater inflator;
	
	/** The preferences. */
	private SharedPreferences preferences;
	
	/** The favourites. */
	private Set<String> favourites;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);
		setupAutoSuggest();
		layoutList = (LinearLayout) findViewById(R.id.list_fav);
		this.preferences = getSharedPreferences();
		inflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		favourites = preferences.getStringSet(Constants.FAVOURITES, null);
		createControlsFromPreferences();
	}
	
	/**
	 * Creates the already selected favorite places controls from the shared preferences data.
	 */
	private void createControlsFromPreferences() {
		if(preferences.contains(Constants.FAVOURITES)) {
			Set<String> favourites = preferences.getStringSet(Constants.FAVOURITES, null);
			for(String place : favourites) {
				LinearLayout layout = (LinearLayout) inflator.inflate(R.layout.fav_layout, null);
				CustomFavouriteContainer container = new CustomFavouriteContainer(layout, place);
				layoutList.addView(layout);
				container.setListener(this);
			}
		}
	}
	
	/**
	 * Validates the current favorite set and adds to it
	 * if the place does not exist and there's less than 10 favorite elements.
	 * @param woeId the woe id
	 * @param placeName the place name
	 */
	private void addToFavourites(String woeId, String placeName) {
		if(this.favourites == null) {
			this.favourites = new HashSet<String>();
		}
		StringBuilder sb = new StringBuilder(woeId);
		String setKey = sb.append(Constants.PATTERN).append(placeName).toString();
		if(this.favourites.contains(setKey)) {
			displayMessage(getString(R.string.place_add_error));
		} else if(favourites.size() == MAX_CAPACITY){
			displayMessage(getString(R.string.place_add_max_error));
		} else {
			addControl(woeId, placeName);
			favourites.add(setKey);
			Editor editor = preferences.edit();
			editor.putStringSet(Constants.FAVOURITES, favourites);
			editor.commit();			
		}
	}
	
	/**
	 * Adds the favorite place to activity screen.
	 *
	 * @param woeId the woe id
	 * @param placeName the place name
	 */
	private void addControl(String woeId, String placeName) {
		LinearLayout layout = (LinearLayout) inflator.inflate(R.layout.fav_layout, null);
		layoutList.addView(layout);
		CustomFavouriteContainer container = new CustomFavouriteContainer(layout, woeId, placeName);
		container.setListener(this);
	}

	@Override
	public void postResults(Channel weatherChanel, String woeid) {
	}

	@Override
	protected void searchWeather(YahooWeatherQuery query) {		
	}

	@Override
	protected void autoSuggestClickAction(Place place) {
		if(this.preferences != null) {
			addToFavourites(place.getWoeid(), place.getName());
		}
	}

	@Override
	public void onViewClick(String woeId) {
		if(this.preferences != null) {
			Editor editor = preferences.edit();
			editor.putString(Constants.WOEID, woeId);
			editor.commit();
			NavUtils.navigateUpFromSameTask(FavouriteActivity.this);
		}
	}
	
	@Override
	protected void refresh() {}
	
	@Override
	public void onRemoveClick(ViewGroup parent, String fullText) {
		if(favourites != null) {
			favourites.remove(fullText);
			preferences.edit().commit();
			parent.setVisibility(View.INVISIBLE);
			layoutList.removeView(parent);
		}		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		layoutList.removeAllViews();
	}

	@Override
	protected void favoriteButtonAction() {
	}
}
