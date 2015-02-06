package com.kanishk.weather.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kanishk.constants.Constants;

/**
 * The Class RestClient. The client has a static methods to make a call to a
 * URL.
 */
public class RestClient {
	
	/** The Constant GSON_PARSER. Use a single parser object instead of creating 
	 *  different one for every request. The methods we used are thread safe with every
	 *  method having its own stack.
	 *  The thread safety issues of Gson have been resolved:
	 *  https://code.google.com/p/google-gson/issues/detail?id=63 
	 *  */
	private static final Gson GSON_PARSER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	/** The Constant HTTP_CONNECTION_TIMEOUT. */
	private static final int HTTP_CONNECTION_TIMEOUT = 7000;
	
	/** The Constant HTTP_READ_TIMEOUT. */
	private static final int HTTP_READ_TIMEOUT = 7000;
	
	/**
	 * Gets the query result. Makes an HTTP call to the specified URL and parses
	 * the response into the object of the specified class. Currently this
	 * method is designed to handle only JSON responses.
	 *
	 * @param <T>            the generic type
	 * @param url            the url to which rest call is to be made
	 * @param classP            the class of the return type value
	 * @return the query result
	 * @throws IOException             Signals that an I/O exception has occurred.
	 * @throws MalformedURLException             the malformed url exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static <T> T getQueryResult(String url, Class<T> classP)
			throws IOException, MalformedURLException, InterruptedException {
		T output;
		try {
			String out = returnUrlStreamString(url);
			/*
			 * Parse the JSON response into Java object of the class type
			 * provided. We had to choose between Jackson and Gson parser
			 * library. Even though various articles on the internet said
			 * Jackson reads faster, we found the performance of Gson better.
			 */
			// Stop operation if the current thread is interrupted
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			output = GSON_PARSER.fromJson(out, classP);
			return output;
		} finally {

		}
	}

	/**
	 * Returns the string output from the url.
	 *
	 * @param url            the url
	 * @return the string returned from http stream
	 * @throws IOException             Signals that an I/O exception has occurred.
	 * @throws InterruptedException             the interrupted exception
	 */
	private static String returnUrlStreamString(String url) throws IOException,
			InterruptedException {
		URL urlValue = null;
		HttpURLConnection conn = null;
		InputStream connecttionStream = null;
		String output;
		try {
			urlValue = new URL(url);
			if (Thread.interrupted()) {
				return null;
			}
			conn = (HttpURLConnection) urlValue.openConnection();
			// Set timeouts for connections and read
			conn.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
			conn.setReadTimeout(HTTP_READ_TIMEOUT);
			connecttionStream = conn.getInputStream();
			
			// Stop operation if the current thread is interrupted
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			// Read the response from the stream as a string
			BufferedReader r = new BufferedReader(new InputStreamReader(
					connecttionStream, Constants.UTF8));
			StringBuilder sb = new StringBuilder();
			String str = r.readLine();
			while (str != null) {
				sb.append(str);
				str = r.readLine();
			}
			output = sb.toString();
			return output;
		} catch(SocketTimeoutException e) {
			throw new IOException(e);
		}
		finally {
			if (connecttionStream != null) {
				connecttionStream.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * Gets the string output from the url and parses it into JSONObject type.
	 * 
	 * @param url
	 *            the url
	 * @return the the JSONObject parsed using the string.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws JSONException
	 *             the JSON exception
	 */
	public static JSONObject getQueryResult(String url) throws IOException,
			MalformedURLException, InterruptedException, JSONException {
		String json = returnUrlStreamString(url);
		JSONObject jsonObj = new JSONObject(json);
		return jsonObj;
	}

	/**
	 * Checks for network connection.
	 * 
	 * @param connManager
	 *            the connectivity manager of the system services
	 * @return true, if there is network access
	 */
	public static boolean hasNetwork(ConnectivityManager connManager) {
		return connManager.getActiveNetworkInfo() != null
				&& connManager.getActiveNetworkInfo().isConnected();
	}
}
