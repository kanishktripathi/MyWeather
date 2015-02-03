package com.kanishk.weather.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.kanishk.constants.Constants;

/**
 * The Class RestClient. The client has a static method to make a call to a URL.
 */
public class RestClient {
	
	/**
	 * Gets the query result. Makes an HTTP call to the specified URL and parses the response into
	 * the object of the specified class. Currently this method is designed to handle only JSON responses.
	 * @param <T> the generic type
	 * @param url the url to which rest call is to be made
	 * @param classP the class of the return type value
	 * @return the query result
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws MalformedURLException the malformed url exception
	 * @throws InterruptedException 
	 */
	public static <T> T getQueryResult(String url, Class<T> classP) throws IOException, MalformedURLException, 
						InterruptedException {
		URL urlValue = null;
		HttpURLConnection conn = null;
		InputStream connecttionStream = null;
		T output;
		try {
			urlValue = new URL(url);
			if(Thread.interrupted()) {
				return null;
			}
			conn = (HttpURLConnection) urlValue.openConnection();
			connecttionStream = conn.getInputStream();
			//Stop operatio if the current thread is interrupted
			if(Thread.interrupted()) {
				throw new InterruptedException();
			}
			//Read the response from the stream as a string
			BufferedReader r = new BufferedReader(new InputStreamReader(connecttionStream, Constants.UTF8));
			StringBuilder sb = new StringBuilder();
			String str = r.readLine();
			while(str != null) {
				sb.append(str);
				str = r.readLine();
			}
			str = sb.toString();
			/*Parse the JSON response into Java object of the class type provided. We had to choose
			 * between Jackson and Gson parser library. Even though various articles on the internet
			 * said Jackson reads faster, we found the performance of Gson better.*/
			Gson gson = new Gson();
			//Stop operatio if the current thread is interrupted
			if(Thread.interrupted()) {
				throw new InterruptedException();
			}
			output = gson.fromJson(str, classP);
			return output;
		}
		finally {
			if(connecttionStream != null) {
				connecttionStream.close();				
			}
			if(conn != null) {
				conn.disconnect();				
			}
    	}
	}
	
	
	/**
	 * Checks for network connection.
	 * @param connManager the connectivity manager of the system services
	 * @return true, if there is network access
	 */
	public static boolean hasNetwork(ConnectivityManager connManager) {
		return connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isConnected();
	}
}
