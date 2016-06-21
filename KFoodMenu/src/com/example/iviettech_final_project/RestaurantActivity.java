package com.example.iviettech_final_project;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import com.example.iviettech_final_project.loading.ImageLoader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantActivity extends FragmentActivity implements OnClickListener {
	static int NUM_ITEMS;
	ImageFragmentPagerAdapter imageFragmentPagerAdapter;
	String imagesURL;
	static String []imagePath;
	static ImageLoader imageLoader;
	String distance;
	
	ImageView ivFavorite;
	ImageView ivShare;
	
	private static final String URL_IMAGES = "http://enddev.site50.net/iViettechFinalProject/images/imagerestaurant/";
	private GPSTracker gps;
	Restaurant restaurant;
	Button locationRestaurant, menuButton;
	TextView tvNameRestaurant, tvTimeOpenClose, tvAddress, tvPhone, tvDistance;
	ViewPager showPager;
	SharedPreferences.Editor editor;
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		Intent calledIntent = getIntent();
		restaurant = (Restaurant) calledIntent.getSerializableExtra("Restaurant");
		imagesURL = restaurant.getImages();
		imagePath = imagesURL.split(";");
		NUM_ITEMS = imagePath.length;
		imageLoader = new ImageLoader(this);
		Log.i("RestaurantActivity", NUM_ITEMS + "");
		double currentLat = 0;
		double currentLong = 0;
		gps = new GPSTracker(RestaurantActivity.this);
		if (gps.canGetLocation()) {
			currentLat = gps.getLatitude();
			currentLong = gps.getLongitude();
			Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + currentLat + "\nLong: " + currentLong, Toast.LENGTH_LONG).show();   
			
		}
		else {
			gps.showSettingsAlert();
		}
		double latX = Double.parseDouble(restaurant.getLatX());
		double latY = Double.parseDouble(restaurant.getLatY());
		new DistanceTask().execute(latX, latY, currentLat, currentLong);

		showPager = (ViewPager) findViewById(R.id.show_image_res);
		imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager());
		showPager.setAdapter(imageFragmentPagerAdapter);
	    locationRestaurant = (Button) findViewById(R.id.bt_location_res);
	    menuButton = (Button) findViewById(R.id.btnmenu);
	    menuButton.setOnClickListener(this);
	    locationRestaurant.setOnClickListener(this);
	    
	    tvNameRestaurant = (TextView) findViewById(R.id.txtname);
	    tvTimeOpenClose = (TextView) findViewById(R.id.txt_time_open_close);
	    tvAddress = (TextView) findViewById(R.id.txt_address);
	    tvPhone = (TextView) findViewById(R.id.txt_phone);
	    tvDistance = (TextView) findViewById(R.id.txt_distance);
	    ivFavorite = (ImageView) findViewById(R.id.iv_favorite);
	    settings = getApplicationContext().getSharedPreferences("UserPreferences", 0);
    	editor = settings.edit();
    	ivFavorite.setTag(R.drawable.article_like_unactive_icon);
	    String []favorite = settings.getString("favorite", "").split(";");
	    for (int i = 0; i < favorite.length; i++) {
	    	if (restaurant.getId().equals(favorite[i])) {
	    		ivFavorite.setImageResource(R.drawable.article_like_active_icon);
		    	ivFavorite.setTag(R.drawable.article_like_active_icon);
	    	}
	    }
	    
	    ivShare = (ImageView) findViewById(R.id.iv_share);
	    ivFavorite.setOnClickListener(this);
	    ivShare.setOnClickListener(this);
	    tvNameRestaurant.setText(restaurant.getRestaurantName());
	    tvTimeOpenClose.setText(restaurant.getTimeOpen() + " - " + restaurant.getTimeClose());
	    tvAddress.setText(restaurant.getAddress());
	    tvPhone.setText(restaurant.getPhone());
	    
	    
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_location_res:
			goLocationRestaurant();
			break;
		case R.id.btnmenu:
			startMenuActivity();
			break;
		case R.id.iv_favorite:
			submitFavorite();
			break;
		case R.id.iv_share:
			share();
			break;
		default:
			break;
		}
		
	}
	private void startMenuActivity() {
		Intent menuIntent = new Intent(RestaurantActivity.this, MenuActivity.class);
		menuIntent.putExtra("Restaurant", restaurant);
		startActivity(menuIntent);
	}
	private void submitFavorite() {
		   Integer integer = (Integer) ivFavorite.getTag();
		   integer = integer == null ? 0 : integer;
		   switch(integer) {
		    case R.drawable.article_like_unactive_icon:
		    	ivFavorite.setImageResource(R.drawable.article_like_active_icon);
		    	ivFavorite.setTag(R.drawable.article_like_active_icon);
		    	String favorite = settings.getString("favorite", "");
		    	favorite += (restaurant.getId() + ";");
		    	editor.putString("favorite", favorite);
		    	editor.apply();
		    	break;
		    case R.drawable.article_like_active_icon:
		    	ivFavorite.setImageResource(R.drawable.article_like_unactive_icon);
		    	ivFavorite.setTag(R.drawable.article_like_unactive_icon);
		    	String favoriteStr = settings.getString("favorite", "");
		    	String newFavorite = favoriteStr.replace(restaurant.getId() + ";", "");
		    	Log.i("RestaurantActivity", newFavorite);
		    	editor.putString("favorite", newFavorite);
		    	editor.apply();
		    	break;
		    default:
		     break;
		   }
	}
	
	private void share() {
		String shareStr = restaurant.getRestaurantName() + " - " + restaurant.getAddress() + " - " + restaurant.getPhone();
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, shareStr );
		startActivity(Intent.createChooser(share, "Share via"));
	}
	void goLocationRestaurant() {
		Intent mapIntent = new Intent(RestaurantActivity.this, MapActivity.class);
		mapIntent.putExtra("ClassForm", RestaurantActivity.class.toString());
		mapIntent.putExtra("Restaurant", restaurant);
		startActivity(mapIntent);
	}
	
	public static class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        public ImageFragmentPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends android.support.v4.app.Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            String imgResPath = URL_IMAGES + imagePath[position];
            imageLoader.DisplayImage(imgResPath, R.drawable.no_logo, imageView);
            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }
    
    private Location getCurrentLocation() {
    	Location location = null;
    	Log.i("RestaurantActivity", "Get Current Location");
    	try {
    		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    		Criteria criteria = new Criteria();
    		location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
    		
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
		return location;
    }
    
    private String getDistanceOnRoad(double latitude, double longitude,
            double prelatitute, double prelongitude) {
        String result_in_kms = "";
        String url = "http://maps.google.com/maps/api/directions/xml?origin="
                + latitude + "," + longitude + "&destination=" + prelatitute
                + "," + prelongitude + "&sensor=false&units=metric";
        String tag[] = { "text" };
        HttpResponse response = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            response = httpClient.execute(httpPost, localContext);
            InputStream is = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(is);
            if (doc != null) {
                NodeList nl;
                ArrayList args = new ArrayList();
                for (String s : tag) {
                    nl = doc.getElementsByTagName(s);
                    if (nl.getLength() > 0) {
                        Node node = nl.item(nl.getLength() - 1);
                        args.add((node).getTextContent());
                    } else {
                        args.add(" - ");
                    }
                }
                result_in_kms = String.format("%s", args.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result_in_kms;
    } 
	
    private class DistanceTask extends AsyncTask<Double, Void, String> {

		@Override
		protected String doInBackground(Double... params) {
			String km = getDistanceOnRoad(params[0], params[1], params[2], params[3]);
			return km;
		}
		@Override
		protected void onPostExecute(String result) {
			distance = result;
			tvDistance.setText(distance);
			Log.i("Distance", distance);
		}
    	
    }
}
