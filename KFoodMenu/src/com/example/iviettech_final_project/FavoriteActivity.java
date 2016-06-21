package com.example.iviettech_final_project;

import java.util.ArrayList;

import org.json.JSONObject;

import com.example.iviettech_final_project_database.JSONFunctions;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class FavoriteActivity extends Activity implements OnItemClickListener {

	private static final String KEY_ID_RES = "ID";
	private static final String KEY_RESTAURANT_NAME = "Name";
	private static final String KEY_TIME_OPEN = "OpenTime";
	private static final String KEY_TIME_CLOSE = "CloseTime";
	private static final String KEY_LAT_X = "LatX";
	private static final String KEY_LAT_Y = "LatY";
	private static final String KEY_CITY_RES = "City";
	private static final String KEY_RANK = "Rank";
	private static final String KEY_PHONE = "Phone";
	private static final String KEY_ADDRESS = "Address";
	private static final String KEY_LOGO = "Logo";
	private static final String KEY_IMAGES = "Images";
	
	ListView restaurantListView;
	RestaurantArrayAdapter restaurantArrayAdapter;
	ArrayList<Restaurant> restaurantArrayList;
	String []favorite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		restaurantListView = (ListView) findViewById(R.id.lv_favorite);
		restaurantListView.setOnItemClickListener(this);
		restaurantArrayList = new ArrayList<Restaurant>();
		restaurantArrayAdapter = new RestaurantArrayAdapter(this, R.layout.restaurant_listview, restaurantArrayList);
		restaurantListView.setAdapter(restaurantArrayAdapter);
		SharedPreferences settings = getApplicationContext().getSharedPreferences("UserPreferences", 0);
		String favoriteRestaurants = settings.getString("favorite", "");
		if (favoriteRestaurants.equals("")) {
			return;
		}
		favorite = favoriteRestaurants.split(";");
		int numRestaurant = favorite.length;
		for (int i = 0; i < numRestaurant; i++) {
			new DownloadFavoriteRestaurant().execute(favorite[i]);
		}
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
	private class DownloadFavoriteRestaurant extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			JSONFunctions jsonFunctions = new JSONFunctions();
			ArrayList<JSONObject> jsonArray = jsonFunctions.getFavoriteRestaurant(params[0]);
			if (jsonArray == null) return null;
			JSONObject json_res = jsonArray.get(0);
			Restaurant res = new Restaurant();
			try {
				res.setId(json_res.getString(KEY_ID_RES));
				res.setRestaurantName(json_res.getString(KEY_RESTAURANT_NAME));
				res.setTimeClose(json_res.getString(KEY_TIME_CLOSE));
				res.setTimeOpen(json_res.getString(KEY_TIME_OPEN));
				res.setLatX(json_res.getString(KEY_LAT_X));
				res.setLatY(json_res.getString(KEY_LAT_Y));
				res.setCity(json_res.getString(KEY_CITY_RES));
				res.setRank(json_res.getString(KEY_RANK));
				res.setPhone(json_res.getString(KEY_PHONE));
				res.setAddress(json_res.getString(KEY_ADDRESS));
				res.setLogo(json_res.getString(KEY_LOGO));
				res.setImages(json_res.getString(KEY_IMAGES));
				restaurantArrayList.add(res);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			restaurantArrayAdapter.notifyDataSetChanged();
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Restaurant restaurant = restaurantArrayList.get(position);
		Intent restaurantIntent = new Intent(FavoriteActivity.this, RestaurantActivity.class);
		restaurantIntent.putExtra("Restaurant", restaurant);
		startActivity(restaurantIntent);
		
	}
	

}
