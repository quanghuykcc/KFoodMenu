package com.example.iviettech_final_project;


import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    
        
        //create tab host
        TabHost tabHost = getTabHost();
        
        //tab for show
        TabSpec showSpec = tabHost.newTabSpec("Show");
        showSpec.setIndicator("", getResources().getDrawable(R.drawable.food_normal));
        Intent showIntent = new Intent(this, FavoriteActivity.class);
        showSpec.setContent(showIntent);
        
        //tab for search
        TabSpec searchSpec = tabHost.newTabSpec("Search");
        searchSpec.setIndicator("", getResources().getDrawable(R.drawable.ic_search));
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchSpec.setContent(searchIntent);
        
       
        
        //tab for map
        TabSpec mapSpec = tabHost.newTabSpec("Map");
        mapSpec.setIndicator("", getResources().getDrawable(R.drawable.ic_map));
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapSpec.setContent(mapIntent);
        
       
        
        //tab for user
        TabSpec userSpec = tabHost.newTabSpec("User");
        userSpec.setIndicator("", getResources().getDrawable(R.drawable.ic_user));
        Intent userIntent = new Intent(this, UserActivity.class);
        userSpec.setContent(userIntent);
        
        //add tab to tabhost
        tabHost.addTab(showSpec);
        tabHost.addTab(searchSpec);
        tabHost.addTab(mapSpec);
        tabHost.addTab(userSpec);
   

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

 

}
