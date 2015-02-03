package com.kanishk.weather.activity;

import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import com.kanishk.constants.Temperature;
import com.kanishk.weather.R;
import com.kanishk.weather.client.YahooWeatherImageTask;
import com.kanishk.yahoo.pojo.weather.Astronomy;
import com.kanishk.yahoo.pojo.weather.Atmosphere;
import com.kanishk.yahoo.pojo.weather.Channel;
import com.kanishk.yahoo.pojo.weather.Condition;
import com.kanishk.yahoo.pojo.weather.Forecast_;
import com.kanishk.yahoo.pojo.weather.Item;
import com.kanishk.yahoo.pojo.weather.Wind;

/**
 * The Class WeatherUpdateContainer. This class contains reference to all UI
 * elements of the WeatherActivity class. Any weather updates made on UI
 * elements should be made through this class to keep the code of trivial UI
 * updates a separate task. Make sure this is only executed from a UI thread.
 */
public final class WeatherUpdateContainer {

	/** The place. */
	private TextView place;

	/** The temperature. */
	private TextView temperature;

	/** The celcius faren. */
	private TextView celciusFaren;

	/** The weather text. */
	private TextView weatherCondition;

	/** The today. */
	private TextView today;

	/** The today max. */
	private TextView todayMax;

	/** The today min. */
	private TextView todayMin;

	/** The weather image. */
	private ImageView weatherImage;

	/** The sun rise. */
	private TextView sunRise;

	/** The sun set. */
	private TextView sunSet;

	/** The feels like. */
	private TextView feelsLike;

	/** The humidity. */
	private TextView humidity;

	/** The wind speed. */
	private TextView windSpeed;

	/** The pressure. */
	private TextView pressure;

	/** The forecast day1. */
	private TextView forecastDay1;

	/** The forecast img1. */
	private ImageView forecastImg1;

	/** The forecast max1. */
	private TextView forecastMax1;

	/** The forecast min1. */
	private TextView forecastMin1;

	/** The forecast day2. */
	private TextView forecastDay2;

	/** The forecast img2. */
	private ImageView forecastImg2;

	/** The forecast max2. */
	private TextView forecastMax2;

	/** The forecast min2. */
	private TextView forecastMin2;

	/** The forecast day3. */
	private TextView forecastDay3;

	/** The forecast img3. */
	private ImageView forecastImg3;

	/** The forecast max3. */
	private TextView forecastMax3;

	/** The forecast min3. */
	private TextView forecastMin3;

	/** The forecast day4. */
	private TextView forecastDay4;

	/** The forecast img4. */
	private ImageView forecastImg4;

	/** The forecast max4. */
	private TextView forecastMax4;

	/** The forecast min4. */
	private TextView forecastMin4;
	
	private TextView forecastText1;
	
	private TextView forecastText2;
	
	private TextView forecastText3;
	
	private TextView forecastText4;

	/** The temperature texts. */
	private List<TextView> temperatureTexts;

	/**
	 * Instantiates a new weather update container. Use it to initialize an
	 * instanr from Weather activity class.
	 * 
	 * @param context
	 *            the context
	 */
	public WeatherUpdateContainer(WeatherActivity context) {
		temperatureTexts = new ArrayList<TextView>(12);
		place = (TextView) context.findViewById(R.id.place_name);
		temperature = (TextView) context.findViewById(R.id.place_temper);
		temperatureTexts.add(temperature);
		weatherCondition = (TextView) context.findViewById(R.id.place_weather);
		today = (TextView) context.findViewById(R.id.today_text);
		todayMax = (TextView) context.findViewById(R.id.max);
		todayMin = (TextView) context.findViewById(R.id.min);
		temperatureTexts.add(todayMax);
		temperatureTexts.add(todayMin);
		sunRise = (TextView) context.findViewById(R.id.sunrise_text);
		sunSet = (TextView) context.findViewById(R.id.sunset_text);
		feelsLike = (TextView) context.findViewById(R.id.windchill_text);
		humidity = (TextView) context.findViewById(R.id.humidity_text);
		pressure = (TextView) context.findViewById(R.id.pressure_text);
		windSpeed = (TextView) context.findViewById(R.id.wind_text);
		celciusFaren = (TextView) context.findViewById(R.id.temper_degree);
		forecastDay1 = (TextView) context.findViewById(R.id.forecast1);
		forecastDay2 = (TextView) context.findViewById(R.id.forecast2);
		forecastDay3 = (TextView) context.findViewById(R.id.forecast3);
		forecastDay4 = (TextView) context.findViewById(R.id.forecast4);
		forecastMax1 = (TextView) context.findViewById(R.id.forecastMax1);
		forecastMax2 = (TextView) context.findViewById(R.id.forecastMax2);
		forecastMax3 = (TextView) context.findViewById(R.id.forecastMax3);
		forecastMax4 = (TextView) context.findViewById(R.id.forecastMax4);
		forecastMin1 = (TextView) context.findViewById(R.id.forecastMin1);
		forecastMin2 = (TextView) context.findViewById(R.id.forecastMin2);
		forecastMin3 = (TextView) context.findViewById(R.id.forecastMin3);
		forecastMin4 = (TextView) context.findViewById(R.id.forecastMin4);
		forecastText1 = (TextView) context.findViewById(R.id.forecastText1);
		forecastText2 = (TextView) context.findViewById(R.id.forecastText2);
		forecastText3 = (TextView) context.findViewById(R.id.forecastText3);
		forecastText4 = (TextView) context.findViewById(R.id.forecastText4);
		temperatureTexts.add(feelsLike);
		temperatureTexts.add(forecastMax1);
		temperatureTexts.add(forecastMax2);
		temperatureTexts.add(forecastMax3);
		temperatureTexts.add(forecastMax4);
		temperatureTexts.add(forecastMin1);
		temperatureTexts.add(forecastMin2);
		temperatureTexts.add(forecastMin3);
		temperatureTexts.add(forecastMin4);
		forecastImg1 = (ImageView) context.findViewById(R.id.img_weather1);
		forecastImg2 = (ImageView) context.findViewById(R.id.img_weather2);
		forecastImg3 = (ImageView) context.findViewById(R.id.img_weather3);
		forecastImg4 = (ImageView) context.findViewById(R.id.img_weather4);
		weatherImage = (ImageView) context.findViewById(R.id.img_weather);
	}

	/**
	 * Gets the inits the update container.
	 * 
	 * @param activity
	 *            the activity
	 * @return the inits the update container
	 */
	public static WeatherUpdateContainer getInitUpdateContainer(
			WeatherActivity activity) {
		WeatherUpdateContainer container = new WeatherUpdateContainer(activity);
		return container;
	}

	/**
	 * Update elements.
	 * 
	 * @param weather
	 *            the weather
	 */
	public void updateElements(Channel weather) {
		Astronomy astronomy = weather.getAstronomy();
		Atmosphere atmosphere = weather.getAtmosphere();
		Wind wind = weather.getWind();
		Item weatherItem = weather.getItem();
		Condition condition = weather.getItem().getCondition();
		//Update weather text and image fields
		place.setText(weather.getLocation().getCity());
		temperature.setText(condition.getTemp());
		weatherCondition.setText(condition.getText());
		sunRise.setText(astronomy.getSunrise());
		sunSet.setText(astronomy.getSunset());
		windSpeed.setText(wind.getSpeed());
		feelsLike.setText(wind.getChill());
		pressure.setText(atmosphere.getPressure());
		humidity.setText(atmosphere.getHumidity());
		List<Forecast_> forecastList = weatherItem.getForecast();
		imageUpdate(weatherImage, condition.getCode());
		Forecast_ foreCast = forecastList.get(0);
		today.setText(foreCast.getDay());
		todayMax.setText(foreCast.getHigh());
		todayMin.setText(foreCast.getLow());
		foreCast = forecastList.get(1);
		imageUpdate(forecastImg1, foreCast.getCode());
		forecastDay1.setText(foreCast.getDay());
		forecastMax1.setText(foreCast.getHigh());
		forecastMin1.setText(foreCast.getLow());
		forecastText1.setText(foreCast.getText());
		imageUpdate(forecastImg2, foreCast.getCode());
		foreCast = forecastList.get(2);
		forecastDay2.setText(foreCast.getDay());
		forecastMax2.setText(foreCast.getHigh());
		forecastMin2.setText(foreCast.getLow());
		forecastText2.setText(foreCast.getText());
		foreCast = forecastList.get(3);
		imageUpdate(forecastImg3, foreCast.getCode());
		forecastDay3.setText(foreCast.getDay());
		forecastMax3.setText(foreCast.getHigh());
		forecastMin3.setText(foreCast.getLow());
		forecastText3.setText(foreCast.getText());
		foreCast = forecastList.get(4);
		imageUpdate(forecastImg4, foreCast.getCode());
		forecastDay4.setText(foreCast.getDay());
		forecastMax4.setText(foreCast.getHigh());
		forecastMin4.setText(foreCast.getLow());
		forecastText4.setText(foreCast.getText());
	}
	
	private void imageUpdate(ImageView view, String weatherCode) {
		YahooWeatherImageTask.taskExecuteUtil(weatherCode, view);
	}
	
	/**
	 * Update temperature. Updates the temperature according to the unit type
	 * mentioned
	 * 
	 * @param temperatureType
	 *            the temperature type
	 */
	public void updateTemperature(Temperature temperatureType,
			String tempeString) {
		if (Temperature.CELCIUS.equals(temperatureType)) {
			changeToCelcius();
		} else {
			changeToFaren();
		}
		celciusFaren.setText(tempeString);
	}

	/**
	 * Change to faren.
	 */
	private void changeToFaren() {
		for (TextView view : temperatureTexts) {
			String s = view.getText().toString();
			float value = (Integer.parseInt(s) * 1.8f);
			value += 32;
			Integer faren = Math.round(value);
			view.setText(faren.toString());
		}
	}

	/**
	 * Change to celcius.
	 */
	private void changeToCelcius() {
		for (TextView view : temperatureTexts) {
			String s = view.getText().toString();
			float value = ((Integer.parseInt(s) - 32) * 5);
			value /= 9;
			Integer cel = Math.round(value);
			view.setText(cel.toString());
		}
	}
}
