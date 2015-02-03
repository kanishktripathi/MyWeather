package com.kanishk.weather.adapter;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.kanishk.weather.R;
import com.kanishk.weather.client.RestClient;
/**
 * The Class SearchAdapter. The generic adapter for performing search operations for places. The method
 * to show the items in the search results is abstract and implemented by user. 
 * extended otherwise also
 * @param <T> the generic type
 */
public abstract class SearchAdapter<T> extends ArrayAdapter<T> implements
		Filterable {

	/** The list. */
	private List<T> list;

	/** The resource. */
	private int resource;

	/**
	 * Instantiates a new search adapter.
	 *
	 * @param context the context
	 * @param resource the resource
	 */
	public SearchAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public T getItem(int position) {
		if (list != null) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View returnView = convertView;
		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			returnView = inflator.inflate(resource, parent, false);
		}
		String listEntry = getViewText(position);
		TextView renderView = (TextView) returnView.findViewById(R.id.listView);
		renderView.setText(listEntry);
		return returnView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results != null && results.count > 0) {
					list = (List<T>) results.values;
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}

			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();;
				if(constraint != null) {
					ConnectivityManager manager = (ConnectivityManager) getContext()
							.getSystemService(Context.CONNECTIVITY_SERVICE);					
					if (RestClient.hasNetwork(manager)) {
						List<T> placeList = getResultList(constraint.toString());
						results.values = placeList;
						results.count = placeList.size();
					} else {
						Context context = getContext();
						Toast.makeText(context, context.getString(R.string.net_connect_error), 
								Toast.LENGTH_SHORT).show();
					}
				}
				return results;
			}
		};
		return filter;
	}

	/**
	 * Gets the result list. This method must be override to provide the source for the
	 * adapter.
	 * @param searchValue the search value on which the results will be obtained
	 * @return the result list
	 */
	public abstract List<T> getResultList(String searchValue);

	/**
	 * Gets the view text. Returns the string value which is to be displayed as a search result.
	 *
	 * @param position the position
	 * @return the view text
	 */
	public abstract String getViewText(int position);

}
