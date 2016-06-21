package com.example.iviettech_final_project;

import com.example.iviettech_final_project_database.JSONFunctions;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordDialog extends Dialog implements android.view.View.OnClickListener{
	Context context;
	Button changePassword, cancel;
	EditText currentPassword, newPassword, confirmPassword;
	TextView error;
	SharedPreferences.Editor editor;
	String username;
	String password;
	public ChangePasswordDialog(Context context) {
		super(context);
		this.context = context;
		setTitle("Change Password");
		setContentView(R.layout.change_password);
		SharedPreferences settings = context.getSharedPreferences("UserPreferences", 0);
		editor = settings.edit();
		username = settings.getString("username", "");
		password = settings.getString("password", "");
		Log.i("Change Password", username + " " + password);
		changePassword = (Button) findViewById(R.id.bt_submit_pass);
		currentPassword = (EditText) findViewById(R.id.current_password);
		newPassword = (EditText) findViewById(R.id.new_password);
		confirmPassword = (EditText) findViewById(R.id.confirm_new_password);
		error = (TextView) findViewById(R.id.tv_error_password);
		cancel = (Button) findViewById(R.id.bt_cancel_pass);
		changePassword.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_submit_pass:
			submitPassword();
			break;
		case R.id.bt_cancel_pass:
			dismiss();
			break;
		default:
			break;
		}
		
	}
	
	private void submitPassword() {
		String currentPasswordStr = currentPassword.getText().toString().trim();
		String newPasswordStr = newPassword.getText().toString().trim();
		String confirmPasswordStr = confirmPassword.getText().toString().trim();
		if (!currentPasswordStr.equals(password)) {
			error.setText("Mật khẩu hiện tại không đúng!");
			currentPassword.setText("");
			newPassword.setText("");
			confirmPassword.setText("");
		}
		else if (!newPasswordStr.equals(confirmPasswordStr)) {
			error.setText("Mật khẩu mới và mật khẩu xác nhận không khớp!");
			currentPassword.setText("");
			newPassword.setText("");
			confirmPassword.setText("");
			
		}
		else {
			error.setText("");
			new ChangePassword().execute(username, "Password", newPasswordStr);
		}
	}
	
	private class ChangePassword extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			JSONFunctions jsonFunctions = new JSONFunctions();
			jsonFunctions.submitUserServer(params[0], params[1], params[2]);
			editor.putString("password", params[2]);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(getContext(), "Bạn đã thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT);
			dismiss();
		}
		
	}
}
