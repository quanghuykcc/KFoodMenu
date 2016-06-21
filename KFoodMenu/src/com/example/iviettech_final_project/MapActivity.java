package com.example.iviettech_final_project;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class MapActivity extends Activity implements OnMarkerClickListener {
	GoogleMap googleMap;
	ProgressDialog loadDialog;
	ArrayList<Restaurant> restaurants;
	MarkerOptions[] markerOptions;
	Marker[] markers;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        loadDialog = new ProgressDialog(this);
        loadDialog.setTitle("Đang tải Map ...");
        loadDialog.setMessage("Vui lòng đợi ... ");
        loadDialog.setCancelable(true);
        loadDialog.show();
        
        restaurants = new ArrayList<Restaurant>();
        int sizeRes = restaurants.size(); 
        
        markerOptions = new MarkerOptions[sizeRes];
        markers = new Marker[sizeRes];
        
        
        try {
        	
        	
        	if (googleMap == null) {
        		googleMap = ((MapFragment) getFragmentManager().
        				findFragmentById(R.id.map_google)).getMap();
        		googleMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
					
					@Override
					public void onMapLoaded() {
						loadDialog.dismiss();
						
					}
				});
        		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        		googleMap.setMyLocationEnabled(true);
        		googleMap.getUiSettings().setZoomControlsEnabled(true);
        		/*for (int i = 0; i < sizeRes; i++) {
                	Restaurant restaurant = restaurants.get(i);
                	Log.i("Map", restaurant.getLatX());
                	Log.i("Map", restaurant.getLatY());
                	double latX = Double.parseDouble(restaurant.getLatX());
                	double latY = Double.parseDouble(restaurant.getLatY()); 
                	Log.i("Map", latX + "");
                	Log.i("Map", latY + "");
                	LatLng latLng = new LatLng(latX, latY);
                	markerOptions[i] = new MarkerOptions();
                	markerOptions[i].position(latLng);
                	markerOptions[i].title(restaurant.getRestaurantName()).snippet(restaurant.getAddress());
                	markerOptions[i].icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                	markers[i] = googleMap.addMarker(markerOptions[i]);
                }*/
        		moveCameraToCurrentPosition();
        		Intent calledIntent = getIntent();
        		String calledActivity = calledIntent.getStringExtra("ClassForm");
        		Log.i("MapActivity", calledActivity);
        		Log.i("MapActivity", RestaurantActivity.class.toString());
        		if (calledActivity.equals(RestaurantActivity.class.toString())) {
        			Restaurant res = (Restaurant) calledIntent.getSerializableExtra("Restaurant");
        			double latX = Double.parseDouble(res.getLatX());
                	double latY = Double.parseDouble(res.getLatY()); 
                	moveCameraToLatLng(latX, latY, res.getRestaurantName(), res.getAddress());
        		}
        		

        		
        		googleMap.setOnMarkerClickListener(this);
        		
        	}
        }
        catch (Exception ex) {
        	ex.printStackTrace();
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

	@Override
	public boolean onMarkerClick(Marker marker) {
		//marker.showInfoWindow();
		return false;
	}

	private void moveCameraToCurrentPosition() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
		if (location == null) {
			return;
		}
		else {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			LatLng currentLatLng = new LatLng(latitude, longitude);
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		}
		
	}
	
	private void moveCameraToLatLng(double latitude, double longitude, String title, String snippet) {
		
		Log.i("MapActivity", "moveToLatlng");
		LatLng latLng = new LatLng(latitude, longitude);
		MarkerOptions markerOps = new MarkerOptions();
		markerOps.position(latLng);
		markerOps.title(title).snippet(snippet);
		markerOps.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_restaurant));
    	Marker markerRes = googleMap.addMarker(markerOps);
    	markerRes.showInfoWindow();
    	googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
	}
	
	

}
