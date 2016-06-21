package com.example.iviettech_final_project;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iviettech_final_project.loading.ImageLoader;

public class FoodArrayAdapter extends ArrayAdapter<Food> {
	private int resourceID;
	private Context context;
	private static final String URL_IMAGES = "http://enddev.site50.net/iViettechFinalProject/images/imagerestaurant/";
	public FoodArrayAdapter(Context context, int resource,
			List<Food> objects) {
		super(context, resource, objects);
		this.resourceID = resource;
		this.context = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Food foodItem = getItem(position);
		View row = convertView;
		
		if (row == null) {
			LayoutInflater inflater;
			inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(resourceID, null);
		}
		else {
			row = convertView;
		}
		
		
		TextView tvNameFood = (TextView) row.findViewById(R.id.tv_name_food);
		tvNameFood.setText(foodItem.getFoodName());
		ImageView ivFoodLogo = (ImageView) row.findViewById(R.id.iv_logo_food);
		ImageLoader imageLoader = new ImageLoader(context);
		String []logo = foodItem.getImages().split(";");
		imageLoader.DisplayImage(URL_IMAGES + logo[0], R.drawable.no_logo, ivFoodLogo);
		return row;
	}
	
}