package com.example.iviettech_final_project;


import com.example.iviettech_final_project.loading.ImageLoader;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class FoodActivity extends FragmentActivity implements OnClickListener {
	
	private static final String URL_IMAGES = "http://enddev.site50.net/iViettechFinalProject/images/imagerestaurant/";
	static int NUM_ITEMS;
	ImageFragmentPagerAdapter imageFragmentPagerAdapter;
	String imagesURL;
	static String []imagePath;
	static ImageLoader imageLoader;
	
	Button btReview;
	ImageView ivShare;
	TextView tvNameFood, tvCostFood, tvInformation, tvPoint;
	ViewPager showPager;
	
	Food food;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food);
		
		Intent calledIntent = getIntent();
		food = (Food) calledIntent.getSerializableExtra("Food");
		
		tvNameFood = (TextView) findViewById(R.id.tv_name_food);
		tvCostFood = (TextView) findViewById(R.id.tv_cost_food);
		tvInformation = (TextView) findViewById(R.id.tv_information);
		tvPoint = (TextView) findViewById(R.id.tv_point);
		btReview = (Button) findViewById(R.id.bt_review);
		ivShare = (ImageView) findViewById(R.id.iv_share);
		ivShare.setOnClickListener(this);
		btReview.setOnClickListener(this);
		
		tvNameFood.setText(food.getFoodName());
		tvCostFood.setText(food.getCost());
		tvInformation.setText(food.getInformation());
		tvPoint.setText("Points: " + food.getPoint());
		
		
		imagesURL = food.getImages();
		imagePath = imagesURL.split(";");
		NUM_ITEMS = imagePath.length;
		imageLoader = new ImageLoader(this);
		Log.i("FoodActivity", NUM_ITEMS + "");
		
		showPager = (ViewPager) findViewById(R.id.show_image_res);
		imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager());
		showPager.setAdapter(imageFragmentPagerAdapter);
		
		
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_review:
			Intent reviewIntent = new Intent(FoodActivity.this, FeedbackActivity.class);
			reviewIntent.putExtra("Food", food);
			startActivity(reviewIntent);
			break;
	
		case R.id.iv_share:
			String shareStr = food.getFoodName() + " - " + food.getInformation() + " - " + food.getCost();
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			share.putExtra(Intent.EXTRA_TEXT, shareStr );
			startActivity(Intent.createChooser(share, "Share via"));
		default:
			break;
		}
		
	}
}
