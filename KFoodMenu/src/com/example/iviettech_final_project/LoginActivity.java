package com.example.iviettech_final_project;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iviettech_final_project_database.JSONFunctions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	Button createAccountButton;
	Button loginButton;
	TextView loginGuest;
	CheckBox checkBoxKeep;

	EditText username, password;

	private static String KEY_SUCCESS = "success";
	private static final String KEY_IS_GUESS = "is_guess";
	private static final String KEY_IS_KEEP = "is_keep";
	private static final String KEY_ID = "id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_FIRSTNAME = "firstname";
	private static final String KEY_LASTNAME = "lastname";
	private static final String KEY_CITY = "city";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_AGE = "age";
	private static final String KEY_AVATAR = "avatar";
	private static final String KEY_POINT = "point";
	private static final String KEY_FAVORITE = "favorite";
	
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		settings = getApplicationContext().getSharedPreferences("UserPreferences", 0);
    	editor = settings.edit();
    	
    	boolean isKeep = settings.getBoolean(KEY_IS_KEEP, false);
    	if (isKeep) {
    		startMainActivity();
    	}
		username = (EditText) findViewById(R.id.et_username);
		password = (EditText) findViewById(R.id.et_password);

		createAccountButton = (Button) findViewById(R.id.bt_create_account);
		loginButton = (Button) findViewById(R.id.bt_login);

		createAccountButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		loginGuest = (TextView) findViewById(R.id.tv_sign_guest);
		loginGuest.setOnClickListener(this);
		
		checkBoxKeep = (CheckBox) findViewById(R.id.checkbox_keep);

	}
	private class LoginTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			login();
			return null;
		}
		
	}
	
	public void login() {
		String usernameString = username.getText().toString();
		String passwordString = password.getText().toString();
		JSONFunctions jsonFunctions = new JSONFunctions();
		JSONObject json = jsonFunctions.loginUser(usernameString, passwordString);
		try {
			if (json.getString(KEY_SUCCESS) != null) {
			 	String res = json.getString(KEY_SUCCESS);
			 	if (Integer.parseInt(res) == 1) {
			        	// user successfully logged in
			        	//Lưu trữ thông tin chi iết người dùng trong database
			        	
			        	JSONObject json_user = json.getJSONObject("user");
			        	
			        	//Xóa toàn bộ dữ liệu trong Database
			        	
			        	
			        	
			        	final String userLogin = json_user.getString(KEY_USERNAME);
			        	
			        	if (checkBoxKeep.isChecked()) {
			        		editor.putBoolean(KEY_IS_KEEP, true);
			        	}
			        	else {
			        		editor.putBoolean(KEY_IS_KEEP, false);
			        	}
			        	editor.putBoolean(KEY_IS_GUESS, false);
			        	editor.putString(KEY_USERNAME, json_user.getString(KEY_USERNAME));
			        	editor.putString(KEY_PASSWORD, json_user.getString(KEY_PASSWORD));
			        	editor.putString(KEY_EMAIL, json_user.getString(KEY_EMAIL));
			        	editor.putString(KEY_FIRSTNAME, json_user.getString(KEY_FIRSTNAME));
			        	editor.putString(KEY_LASTNAME, json_user.getString(KEY_LASTNAME));
			        	editor.putString(KEY_CITY, json_user.getString(KEY_CITY));
			        	editor.putString(KEY_GENDER, json_user.getString(KEY_GENDER));
			        	editor.putString(KEY_AGE, json_user.getString(KEY_AGE));
			        	editor.putString(KEY_AVATAR, json_user.getString(KEY_AVATAR));
			        	editor.putString(KEY_POINT, json_user.getString(KEY_POINT));
			        	editor.putString(KEY_FAVORITE, json_user.getString(KEY_FAVORITE));
			        	editor.apply();
			        	
			        	
			        	runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(LoginActivity.this, userLogin + ", bạn đã đăng nhập thành công", Toast.LENGTH_SHORT).show();				
							}
						});
			        	
			        	startMainActivity();	
			        	
			 	}
			 	else {
			 		runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
							username.setText("");
					 		password.setText("");
					 		username.requestFocus();
						}
					});
			 		
			 		
			 	}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt_create_account:
			RegisterDialog registerDialog = new RegisterDialog(this);
			registerDialog.show();
			break;
		case R.id.bt_login:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					login();
					
				}
			}).start();
			break;
		
		case R.id.tv_sign_guest:
			settings.edit().clear().commit();
			editor.putBoolean(KEY_IS_GUESS, true);
			editor.apply();
			startMainActivity();
			break;
		default:
			break;
		}

	}
	
	private void startMainActivity() {
		Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
    	mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(mainActivity);
    	finish();
	}

}
