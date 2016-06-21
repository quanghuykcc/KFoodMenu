package com.example.iviettech_final_project;

import java.util.ArrayList;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FeedbackActivity extends Activity implements OnClickListener{
	private static final String KEY_IS_GUESS = "is_guess";
	Food food;
	ArrayList<Feedback> feedbackArrayList;
	ArrayList<JSONObject> jsonArray;
	FeedbackArrayAdapter feedbackArrayAdapter;
	Button sendComment;
	ListView lvFeedback;
	EditText comment;
	String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getApplicationContext().getSharedPreferences("UserPreferences", 0);
		boolean isGuess = settings.getBoolean(KEY_IS_GUESS, false);
		if (isGuess) {
			Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập để bình luận!", Toast.LENGTH_SHORT);
			Intent loginIntent = new Intent(FeedbackActivity.this, LoginActivity.class);
			loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(loginIntent);
		}
		
		setContentView(R.layout.activity_feedback);
		
		
		lvFeedback = (ListView) findViewById(R.id.feedback_listview);
		sendComment = (Button) findViewById(R.id.bt_send_comment);
		comment = (EditText) findViewById(R.id.et_comment);
		sendComment.setOnClickListener(this);
		
		jsonArray = new ArrayList<JSONObject>();
		feedbackArrayList = new ArrayList<Feedback>();
		Intent calledIntent = getIntent();
		food = (Food) calledIntent.getSerializableExtra("Food");
		
    	username = settings.getString("username", "");
    	new DownloadFeedback().execute(food.getId());
    	
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
	
	private class DownloadFeedback extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			JSONFunctions jsonFunctions = new JSONFunctions();
			jsonArray = jsonFunctions.getFeedbackByFood(params[0]);
			if (jsonArray == null) return null;
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.get(i);
				Feedback feedback = new Feedback();
				try {
					feedback.setId(json.getString("ID"));
					feedback.setComment(json.getString("Comment"));
					feedback.setFeedBackAt(json.getString("FeedbackAt"));
					feedback.setFoodID(json.getString("FoodID"));
					feedback.setUserName(json.getString("Username"));
					Log.i("FeedbackActivity", feedback.toString());
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				feedbackArrayList.add(feedback);	
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			feedbackArrayAdapter = new FeedbackArrayAdapter(FeedbackActivity.this, R.layout.feedback_listview, feedbackArrayList);
			lvFeedback.setAdapter(feedbackArrayAdapter);
		}
		
	}
	
	private class SendFeedback extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			JSONFunctions jsonFunctions = new JSONFunctions();
			JSONObject json = jsonFunctions.sendFeedback(params[0], params[1], params[2]);
			Feedback feedback = new Feedback();
			try {
				JSONObject jsonObj = json.getJSONObject("Feedback");
				
				feedback.setId(jsonObj.getString("ID"));
				feedback.setComment(jsonObj.getString("Comment"));
				feedback.setFeedBackAt(jsonObj.getString("FeedbackAt"));
				feedback.setFoodID(jsonObj.getString("FoodID"));
				feedback.setUserName(jsonObj.getString("Username"));
				feedbackArrayList.add(feedback);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			feedbackArrayAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_send_comment:
			String commentStr = comment.getText().toString().trim();
			if (commentStr.equals("")) {
				return;
			}
			new SendFeedback().execute(username, commentStr, food.getId());
			comment.setText("");
			break;

		default:
			break;
		}
		
	}
	
}
