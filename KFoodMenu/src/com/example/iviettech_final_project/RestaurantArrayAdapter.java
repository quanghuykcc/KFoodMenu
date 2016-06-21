package com.example.iviettech_final_project;

import java.util.List;

import com.example.iviettech_final_project.loading.DownloadImageTask;
import com.example.iviettech_final_project.loading.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantArrayAdapter extends ArrayAdapter<Restaurant> {
	private static final String URL_LOGO = "http://enddev.site50.net/iViettechFinalProject/images/logo/";
	private int resourceID;
	private Context context;
	public RestaurantArrayAdapter(Context context, int resource,
			List<Restaurant> objects) {
		super(context, resource, objects);
		this.resourceID = resource;
		this.context = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Restaurant restaurantItem = getItem(position);
		View row = convertView;
		
		if (row == null) {
			LayoutInflater inflater;
			inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(resourceID, null);
		}
		else {
			row = convertView;
		}
		
		ImageLoader imageLoader = new ImageLoader(context);
		int loader = R.drawable.no_logo;
		
		TextView tvNameRestaurant = (TextView) row.findViewById(R.id.tv_name_restaurant);
		TextView tvTimeOpenClose = (TextView) row.findViewById(R.id.tv_time_open_close);
		TextView tvRank = (TextView) row.findViewById(R.id.tv_rank);
		TextView tvPhone = (TextView) row.findViewById(R.id.tv_phone);
		ImageView imageLogo = (ImageView) row.findViewById(R.id.restaurant_logo);
	
		tvNameRestaurant.setText(restaurantItem.getRestaurantName());
		tvTimeOpenClose.setText(restaurantItem.getTimeOpen() + " - " + restaurantItem.getTimeClose());
		tvRank.setText(restaurantItem.getRank());
		tvPhone.setText(restaurantItem.getPhone());
		//new DownloadImageTask(imageLogo).execute(URL_LOGO + restaurantItem.getLogo());
		imageLoader.DisplayImage(URL_LOGO + restaurantItem.getLogo(), loader, imageLogo);
		return row;
	}
	
}
