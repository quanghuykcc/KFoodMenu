package com.example.iviettech_final_project;

import java.util.ArrayList;

import com.example.iviettech_final_project_database.JSONFunctions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity implements OnLongClickListener {
	
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_FIRSTNAME = "firstname";
	private static final String KEY_LASTNAME = "lastname";
	private static final String KEY_CITY = "city";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_AGE = "age";
	private static final String KEY_AVATAR = "avatar";
	private static final String KEY_IS_GUESS = "is_guess";
	
	ArrayList<Integer> editTextID;
	
	Button submitButton;
	EditText firstNameET, lastNameET, passwordET,
			 cityET, genderET, ageET;
	TextView registerErrorMsg, userNameTV, emailTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		SharedPreferences settings = getApplicationContext().getSharedPreferences("UserPreferences", 0);
		if (settings.getBoolean(KEY_IS_GUESS, false)) {
			Intent loginIntent = new Intent(UserActivity.this, LoginActivity.class);
			loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(loginIntent);
			finish();
		}
		
		editTextID = new ArrayList<Integer>();
		
		submitButton = (Button) findViewById(R.id.bt_submit_user);
		submitButton.setVisibility(View.INVISIBLE);
		registerErrorMsg = (TextView) findViewById(R.id.user_error);
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = userNameTV.getText().toString().trim();
				String changed = "";
				String key = "";
				String keyPreferences = "";
				
				SharedPreferences settings = getApplicationContext().getSharedPreferences("UserPreferences", 0);
				SharedPreferences.Editor editor = settings.edit();
				for (int i = 0; i <editTextID.size(); i++) {
					
					switch (editTextID.get(i)) {
					case R.id.et_firstname_user:
						key = "Firstname";
						keyPreferences = "firstname";
						break;
					case R.id.et_lastname_user:
						key = "Lastname";
						keyPreferences = "lastname";
						break;
					case R.id.et_gender_user:
						key = "Gender";
						keyPreferences = "gender";
						break;
					case R.id.et_city_user:
						key = "City";
						keyPreferences = "city";
						break;
					case R.id.et_age_user:
						key = "Age";
						keyPreferences = "age";
						break;
					default:
						break;
					}
					
					
					EditText et = (EditText) findViewById(editTextID.get(i));
					changed = et.getText().toString();
					if (changed.trim().equals("")) {
						registerErrorMsg.setText("Please input all value!");
						return;
					}
					else {
						registerErrorMsg.setText("");
					}	
					editor.putString(keyPreferences, changed);
					new ChangeUserTask().execute(username, key, changed);
				}
				editor.apply();
				for (int i = 0; i < editTextID.size(); i++) {
					EditText et = (EditText) findViewById(editTextID.get(i));
					et.setCursorVisible(false);
					et.setFocusable(false);
					et.setFocusableInTouchMode(false);
					
				}
				submitButton.setVisibility(View.INVISIBLE);
				
			}
		});
		

		firstNameET = (EditText) findViewById(R.id.et_firstname_user);
		lastNameET = (EditText) findViewById(R.id.et_lastname_user);
		userNameTV = (TextView) findViewById(R.id.tv_username_user);
		passwordET = (EditText) findViewById(R.id.et_password_user);
		emailTV = (TextView) findViewById(R.id.tv_email_address_user);
		genderET = (EditText) findViewById(R.id.et_gender_user);
		cityET = (EditText) findViewById(R.id.et_city_user);
		ageET = (EditText) findViewById(R.id.et_age_user);
		
		
		
		lastNameET.setText(settings.getString(KEY_LASTNAME, ""));
		firstNameET.setText(settings.getString(KEY_FIRSTNAME, ""));
		userNameTV.setText(settings.getString(KEY_USERNAME, ""));
		passwordET.setText(settings.getString(KEY_PASSWORD, ""));
		emailTV.setText(settings.getString(KEY_EMAIL, ""));
		genderET.setText(settings.getString(KEY_GENDER, ""));
		cityET.setText(settings.getString(KEY_CITY, ""));
		ageET.setText(settings.getString(KEY_AGE, ""));

		lastNameET.setFocusableInTouchMode(false);
		firstNameET.setFocusableInTouchMode(false);
		passwordET.setFocusableInTouchMode(false);
		genderET.setFocusableInTouchMode(false);
		cityET.setFocusableInTouchMode(false);
		ageET.setFocusableInTouchMode(false);
		
		lastNameET.setOnLongClickListener(this);
		firstNameET.setOnLongClickListener(this);
		passwordET.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				ChangePasswordDialog changeDialog = new ChangePasswordDialog(UserActivity.this);
				changeDialog.show();
				return false;
			}
		});
		genderET.setOnLongClickListener(this);
		cityET.setOnLongClickListener(this);
		ageET.setOnLongClickListener(this);
		
		
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
	public boolean onLongClick(View v) {
		EditText editTextSelected = (EditText) v;
		editTextSelected.setFocusableInTouchMode(true);
		editTextSelected.setFocusable(true);
		editTextID.add(editTextSelected.getId());
		submitButton.setVisibility(View.VISIBLE);
		editTextSelected.requestFocus();
		return false;
	}
	
	private Boolean submitUser(String username, String key, String value) {
		JSONFunctions userFunctions = new JSONFunctions();
		boolean result = userFunctions.submitUserServer(username, key, value);
		return result;
		
	}
	
	private class ChangeUserTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			return submitUser(params[0], params[1], params[2]);

		}
		@Override
		protected void onPostExecute(Boolean result) {
			Toast.makeText(getApplicationContext(), "Updated data successfully", Toast.LENGTH_SHORT).show();
	
		}
		
	}
}
