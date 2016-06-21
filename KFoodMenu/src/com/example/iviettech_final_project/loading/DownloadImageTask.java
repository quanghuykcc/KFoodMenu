package com.example.iviettech_final_project.loading;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bitmapImage;
	
	public DownloadImageTask(ImageView bitmapImage) {
		this.bitmapImage = bitmapImage;
	}
	
	@Override
	protected Bitmap doInBackground(String... urls) {
		String urlDisplay = urls[0];
		Log.i("Load image", "URL " + urlDisplay);
		Bitmap loadBitmap = null;
		try {
			InputStream in = new java.net.URL(urlDisplay).openStream();
			loadBitmap = BitmapFactory.decodeStream(in);
		}
		catch (Exception ex) {
			Log.e("Error", ex.getMessage());
            ex.printStackTrace();
		}
		return loadBitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		bitmapImage.setImageBitmap(result);
	}
	
}
