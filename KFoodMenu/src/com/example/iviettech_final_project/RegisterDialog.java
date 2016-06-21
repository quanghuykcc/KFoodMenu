package com.example.iviettech_final_project;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iviettech_final_project_database.JSONFunctions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterDialog extends Dialog {
	Context context;
	Button submitButton;
	Button cancelButton;
	EditText firstNameET, lastNameET, userNameET, passwordET,
			confirmPasswordET, emailET, cityET, genderET, ageET;
	TextView registerErrorMsg;

	private static String KEY_SUCCESS = "success";
	
	private static final String TAG_NAME = "Register_Dialog";
	
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

	public RegisterDialog(Context context) {
		super(context);
		this.context = context;
		setTitle("Guest Sign On");
		setContentView(R.layout.register_dialog);

		submitButton = (Button) findViewById(R.id.bt_register_ok);
		cancelButton = (Button) findViewById(R.id.bt_register_cancel);

		lastNameET = (EditText) findViewById(R.id.et_firstname);
		firstNameET = (EditText) findViewById(R.id.et_lastname);
		userNameET = (EditText) findViewById(R.id.et_username_register);
		passwordET = (EditText) findViewById(R.id.et_password_register);
		confirmPasswordET = (EditText) findViewById(R.id.et_confirm_password_res);
		emailET = (EditText) findViewById(R.id.et_email_address);
		genderET = (EditText) findViewById(R.id.et_gender_res);
		cityET = (EditText) findViewById(R.id.et_city_res);
		ageET = (EditText) findViewById(R.id.et_age_res);

		registerErrorMsg = (TextView) findViewById(R.id.register_error);

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (firstNameET.getText().toString().trim().equals("")) {
					registerErrorMsg.setText("Please input Firstname!");
					return;
				}
				if (lastNameET.getText().toString().trim().equals("")) {
					registerErrorMsg.setText("Please input Lastname!");
					return;
				}
				if (userNameET.getText().toString().trim().equals("")) {
					registerErrorMsg.setText("Please input Username!");
					return;
				}
				if (emailET.getText().toString().trim().equals("")) {
					registerErrorMsg.setText("Please input Email!");
					return;
				}
				if (passwordET.getText().toString().trim().equals("")) {
					registerErrorMsg.setText("Please input Password!");
					return;
				}
				if (cityET.getText().toString().trim().equals("")) {
					registerErrorMsg.setText("Please input City!");
					return;
				}
				if (confirmPasswordET.getText().toString().trim().equals("")) {
					registerErrorMsg.setText("Please confirm Password!");
					return;
				}
				
				if (!confirmPasswordET.getText().toString().equals(passwordET.getText().toString())) {
					registerErrorMsg.setText("Confirm Password don't match Password");
					return;
				}
				registerErrorMsg.setText("");
				new RegisterTask().execute();
				

			}
		});
	}

	private class RegisterTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			return register();
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				dismiss();
			}
			else {
				registerErrorMsg.setText("User already existed!");
			}
		}
		
	}
	
	private boolean register() {

		boolean result = false;
		String username = userNameET.getText().toString();
		String password = passwordET.getText().toString();
		String email = emailET.getText().toString();
		String firstname = firstNameET.getText().toString();
		String lastname = lastNameET.getText().toString();
		String gender = genderET.getText().toString();
		String age = ageET.getText().toString();
		String city = cityET.getText().toString();

		JSONFunctions jsonFunctions = new JSONFunctions();
		Log.i(TAG_NAME, "Truoc register");
		JSONObject json = jsonFunctions.registerUser(username, password, email,
				firstname, lastname, city, gender,
				age, "");

		
		// Kiểm tra việc phản hồi từ dữ liệu
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);
				Log.i(TAG_NAME, "Kiem tra phan hoi du lieu");
				// Nếu đăng ký thành công
				if (Integer.parseInt(res) == 1) {
					
					dismiss();
					result = true;
				} else {
					result = false;
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;

	}

}
