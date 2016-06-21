package com.example.iviettech_final_project_database;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class JSONFunctions {

	private JSONParser jsonParser;
	
	private final static String TAG_NAME = "UserFunctions";

	private static String loginURL = "http://enddev.site50.net/iViettechFinalProject/";
	private static String registerURL = "http://enddev.site50.net/iViettechFinalProject/";
	private static String changeURL = "http://enddev.site50.net/iViettechFinalProject/";


	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String search_city_tag = "get_restaurant_by_city";
	private static String changed_info_tag = "change_info_user";
	private static String get_food_tag = "get_food_by_restaurant";
	private static String send_feedback_tag = "send_feedback";
	private static String get_feedback_tag = "get_feedback_by_food";
	private static String get_favorite_restaurant_tag = "get_favorite";
	private static String get_restaurant_by_name = "get_restaurant_by_name";
	
	// Hàm xây dựng khởi tạo đối tượng
	public JSONFunctions() {
		jsonParser = new JSONParser();
	}

	// Thực hiện công việc đăng nhập với email và password
	public JSONObject loginUser(String username, String password) {
		// Xây dựng các giá trị
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// Trả về đối tượng là 1 JSONObject
		return json;
	}
	
	public boolean submitUserServer(String username, String key, String value) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", changed_info_tag));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("key", key));
		params.add(new BasicNameValuePair("value", value));
		boolean result = jsonParser.submitUserInformation(changeURL, params);
		return result;
	}
	
	public ArrayList<JSONObject> searchRestaurantByCity(String city) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", search_city_tag));
		params.add(new BasicNameValuePair("city", city));
		ArrayList<JSONObject> jsonArray = jsonParser.getJSONArrayFromUrl(loginURL, params);
		// Trả về đối tượng là 1 JSONObject
		return jsonArray;
	}
	
	public ArrayList<JSONObject> getFoodByRestaurant(String restaurantID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_food_tag));
		params.add(new BasicNameValuePair("restaurantID", restaurantID));
		ArrayList<JSONObject> jsonArray = jsonParser.getJSONArrayFromUrl(loginURL, params);
		// Trả về đối tượng là 1 JSONObject
		return jsonArray;
	}
	
	public ArrayList<JSONObject> getFeedbackByFood(String foodID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_feedback_tag));
		params.add(new BasicNameValuePair("foodID", foodID));
		ArrayList<JSONObject> jsonArray = jsonParser.getJSONArrayFromUrl(loginURL, params);
		// Trả về đối tượng là 1 JSONArray
		return jsonArray;
	}
	
	public ArrayList<JSONObject> getFavoriteRestaurant(String restaurantID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_favorite_restaurant_tag));
		params.add(new BasicNameValuePair("restaurantID", restaurantID));
		ArrayList<JSONObject> jsonArray = jsonParser.getJSONArrayFromUrl(loginURL, params);
		return jsonArray;
	}
	
	public ArrayList<JSONObject> getRestaurantByName(String nameRestaurant) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_restaurant_by_name));
		params.add(new BasicNameValuePair("nameRestaurant", nameRestaurant));
		ArrayList<JSONObject> jsonArray = jsonParser.getJSONArrayFromUrl(loginURL, params);
		return jsonArray;
	}
	
	public JSONObject sendFeedback(String username, String comment, String foodID) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", send_feedback_tag));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("comment", comment));
		params.add(new BasicNameValuePair("foodID", foodID));
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
		
	}
	

	public JSONObject registerUser(String username, String password,
			String email, String firstname, String lastname,
			String city, String gender, String age, String avatar) {
		// Xây dựng các giá trị
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("firstname", firstname));
		params.add(new BasicNameValuePair("lastname", lastname));
		params.add(new BasicNameValuePair("city", city));
		params.add(new BasicNameValuePair("gender", gender));
		params.add(new BasicNameValuePair("age", age));
		params.add(new BasicNameValuePair("avatar", avatar));
		
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		Log.i(TAG_NAME, "Lay Json thanh cong");
		// Trả về đối tượng là 1 JSONObject
		return json;
	}

}
