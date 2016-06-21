package com.example.iviettech_final_project;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.iviettech_final_project.loading.ImageLoader;
import com.example.iviettech_final_project_database.JSONFunctions;

public class MenuActivity extends Activity {
	
	private static final String URL_LOGO = "http://enddev.site50.net/iViettechFinalProject/images/logo/";
	
	
	ListView foodListView;
	FoodArrayAdapter foodArrayAdapter;
	ArrayList<Food> foodArrayList;
	ArrayList<JSONObject> jsonArray;
	ImageView logoRestaurant;
	TextView nameRestaurant;
	Restaurant restaurant;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	
		foodListView = (ListView) findViewById(R.id.listview_menu);
		logoRestaurant = (ImageView) findViewById(R.id.iv_logo_restaurant);
		nameRestaurant = (TextView) findViewById(R.id.tv_name_restaurant);
		jsonArray = new ArrayList<JSONObject>();
		foodArrayList = new ArrayList<Food>();
		Intent calledIntent = getIntent();
		restaurant = (Restaurant) calledIntent.getSerializableExtra("Restaurant");
		nameRestaurant.setText(restaurant.getRestaurantName());
		
		ImageLoader imageLoader = new ImageLoader(this);
		int loader = R.drawable.no_logo;
		imageLoader.DisplayImage(URL_LOGO + restaurant.getLogo(), loader, logoRestaurant);
		
		String restaurantID = restaurant.getId();
		new LoadFoodTask().execute(restaurantID);
		foodListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Food food = foodArrayList.get(position);
				Intent foodIntent = new Intent(MenuActivity.this, FoodActivity.class);
				foodIntent.putExtra("Food", food);
				startActivity(foodIntent);
			}
			
			
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			SharedPreferences settings = settings = getApplicationContext().getSharedPreferences("UserPreferences", 0);
			settings.edit().clear().commit();
			Intent loginIntent = new Intent(this, LoginActivity.class);
			loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(loginIntent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class LoadFoodTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			try {
				JSONFunctions userFunctions = new JSONFunctions();
				jsonArray = userFunctions
						.getFoodByRestaurant(params[0]);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = jsonArray.get(i);
					Food food = new Food();
					try {
						food.setId(json.getString("ID"));
						food.setFoodName(json.getString("FoodName"));
						food.setPoint(json.getString("Point"));
						food.setCost(json.getString("Cost"));
						food.setInformation(json.getString("Information"));
						food.setRestaurantID(json.getString("RestaurantID"));
						food.setImages(json.getString("Images"));
						Log.i("MenuActivity", food.toString());
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
					foodArrayList.add(food);	
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			foodArrayAdapter = new FoodArrayAdapter(MenuActivity.this, R.layout.food_listview, foodArrayList);
			foodListView.setAdapter(foodArrayAdapter);
		}
	}
}
