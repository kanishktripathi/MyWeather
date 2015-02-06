package com.kanishk.weather.activity;

import java.util.regex.Pattern;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kanishk.constants.Constants;
import com.kanishk.weather.R;

/**
 * The Class CustomFavouriteContainer. This is a helper class for storing the values associated with
 * a favorite city. It is not designed to be used anywhere after its initialization. It cleans up its data
 * on the click of remove button.
 */
public class CustomFavouriteContainer implements OnClickListener {
	
	/** The Constant PATTERN. */
	private static final Pattern PATTERN = Pattern.compile(Constants.PATTERN);
	
	/** The woeid. */
	private String woeid;
	
	/** The text. */
	private TextView text;
	
	/** The remove. */
	private ImageButton remove;
	
	/** The parent. */
	private ViewGroup parent;
	
	/** The listener. */
	private FavouriteLayoutListener listener;
	
	/**
	 * Instantiates a new custom favourite container.
	 *
	 * @param parent the parent
	 * @param fullText the full text
	 */
	public CustomFavouriteContainer(ViewGroup parent, String fullText) {
		String[] arr = PATTERN.split(fullText);
		this.woeid = arr[0];
		init(parent, arr[1]);
	}
	
	/**
	 * Instantiates a new custom favourite container.
	 *
	 * @param parent the parent view group under which the place name and remove button
	 * will be displayed
	 * @param woeid the woeid
	 * @param name the name
	 */
	public CustomFavouriteContainer(ViewGroup parent, String woeid, String name) {
		this.woeid = woeid;
		init(parent, name);
	}
		
	private void init(ViewGroup parent, String name) {
		text = (TextView) parent.findViewById(R.id.favText);		
		text.setId(View.NO_ID);
		this.parent = parent;
		text.setText(name);
		remove = (ImageButton) parent.findViewById(R.id.favImg_rem);
		remove.setId(View.NO_ID);
		remove.setOnClickListener(this);
		text.setOnClickListener(this);
	}
	
	/**
	 * Sets the listener.
	 *
	 * @param listener the new listener
	 */
	public void setListener(FavouriteLayoutListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		if(listener == null) {
			return;
		}
		//We used a single listener for both text view and image button click. So this check
		if(v == text) {
			listener.onViewClick(woeid);
		} else {
			StringBuilder sb = new StringBuilder(woeid).append("__").append(this.text.getText());
			String fullText = sb.toString();
			listener.onRemoveClick(parent, fullText);
			cleanUp();
		}
		
	}
	
	/**
	 * Clean up.
	 */
	private void cleanUp() {
		remove = null;
		woeid = null;
		text = null;
		parent = null;
		listener = null;
		parent = null;
	}
	
	/**
	 * The listener interface for receiving favouriteLayout events.
	 * The class that is interested in processing a favouriteLayout
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addFavouriteLayoutListener<code> method. When
	 * the favouriteLayout event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see FavouriteLayoutEvent
	 */
	public static interface FavouriteLayoutListener {
		
		/**
		 * On view click. The method to call on click of the name of the place.
		 *
		 * @param woeId the woe id
		 */
		void onViewClick(String woeId);
		
		/**
		 * On remove click. The event to trigger on the click of remove button.
		 *
		 * @param parent the parent
		 * @param fullText the full text
		 */
		void onRemoveClick(ViewGroup parent, String fullText);
	}
}
