package com.example.iviettech_final_project;

import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;

public class FoodClickListener implements OnClickListener {
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_sign_guest:
			LoginGuest();
			break;

		default:
			break;
		}
	}
	
	public void LoginGuest() {
		
	}

}

