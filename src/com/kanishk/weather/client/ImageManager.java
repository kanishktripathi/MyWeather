package com.kanishk.weather.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * The Class ImageManager. This is a singleton instance that maintains a cache of Bitmap images.
 * This can also be used for Threadpools in future if required.
 */
public class ImageManager {
	
	/** The instance. */
	private static ImageManager instance = null;
	
	
	/** The Constant CACHE_SIZE. Initialized to 50KB */
	private static final int CACHE_SIZE = 50 * 1024; 
	
	/** The image cache. */
	private final LruCache<String, Bitmap> imageCache;
	
	static {
		instance = new ImageManager();
	}
	
	/**
	 * Instantiates a new image manager.
	 */
	private ImageManager() {
		imageCache = new LruCache<String, Bitmap>(CACHE_SIZE) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}
	
	/**
	 * Gets the single instance of ImageManager.
	 *
	 * @return single instance of ImageManager
	 */
	public static ImageManager getInstance() {
		return instance;
	}
	
	/**
	 * Gets the bit map from cache.
	 *
	 * @param key the key
	 * @return the bit map from cache
	 */
	public Bitmap getBitMapFromCache(String key) {
		return imageCache.get(key);
	}
	
	/**
	 * Gets the image bitmap from url. Also adds the bitmap into the LRU image cache.
	 * It first checks whether the image with the given code is already present in the cache or 
	 * not
	 * @param key the image key to put the image in cache.
	 * @param url the url of the image to be bitmapped
	 * @return the image from url
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Bitmap getImageFromUrl(String key, String url) throws IOException {
		Bitmap map = processBitMap(url);
		if(map != null) {
			imageCache.put(key, map);
		}
		return map;
	}
	
	/**
	 * Processes bit map. for the given url. Returns a bitmap corresponding to the url.
	 * Currently we are not managing image size as the source images are small(2KB).
	 * @param url the image url
	 * @return the bitmap for the image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Bitmap processBitMap(String url) throws IOException {
		URL urlVal = new URL(url);
		HttpURLConnection conn = null;
		InputStream connectionStream = null;
		Bitmap map = null;
		try {
			// Connect to the url
			conn = (HttpURLConnection) urlVal.openConnection();
			connectionStream = conn.getInputStream();
			//Decode the stram
			map = BitmapFactory.decodeStream(connectionStream);
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
			if(connectionStream != null) {
				connectionStream.close();
			}
		}
		return map;	
	}
}
