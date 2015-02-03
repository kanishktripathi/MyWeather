package com.kanishk.weather.client;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kanishk.constants.Constants;

/**
 * The Class YahooWeatherImageTask. The async task for setting the image
 * with the given code into the imge view provided. The class maintains a weak 
 * reference to the image view provided.
 */
public class YahooWeatherImageTask extends AsyncTask<Void, Void, Bitmap> {
	
	/** The Constant BASE_IMAGE. */
	private static final String BASE_IMAGE = "http://l.yimg.com/a/i/us/we/52/";
	
	/** The code. */
	private String code;
	
	/** The image reference. */
	private WeakReference<ImageView> imageReference;
	
	/** The img manager. */
	private ImageManager imgManager;
	
	/**
	 * Instantiates a new yahoo weather image task.
	 *
	 * @param code the code
	 * @param imageView the image view
	 */
	public YahooWeatherImageTask(String code, ImageView imageView) {
		this.code = code;
		this.imageReference = new WeakReference<ImageView>(imageView);
		this.imgManager = ImageManager.getInstance();
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the image view.
	 *
	 * @return the image view
	 */
	public WeakReference<ImageView> getImageView() {
		return imageReference;
	}

	/**
	 * Sets the image view.
	 *
	 * @param imageView the new image view
	 */
	public void setImageView(WeakReference<ImageView> imageView) {
		this.imageReference = imageView;
	}

	@Override
	protected Bitmap doInBackground(Void... params) {
		//Check if present in cache
		Bitmap map = imgManager.getBitMapFromCache(code);
		if(map == null) {
			StringBuilder sb = new StringBuilder(BASE_IMAGE);
			sb.append(code).append(Constants.YAHOO_IMAGE_EXTENSION);
			String url = sb.toString();
			try {
				map = imgManager.getImageFromUrl(code, url);
			} catch (IOException e) {
				Log.e(e.toString(), e.getMessage());
			}
		}
		return map;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			if (imageReference != null && imageReference.get() != null) {
				final ImageView view = imageReference.get();
				view.setImageBitmap(result);
			}
		}
	}
	
	/**
	 * Task execute util. A helper utility to execute an image task.
	 * @param code the image code
	 * @param imageView the image view on the activity
	 */
	public static void taskExecuteUtil(String code, ImageView imageView) {
		YahooWeatherImageTask task = new YahooWeatherImageTask(code, imageView);
		task.execute();
	}
}
