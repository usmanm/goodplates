package com.cardsui.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class DishInfoActivity extends Activity {

	public String locuid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_dish_info);
		
		// Populate the view
		TextView mDish = (TextView) findViewById(R.id.dishName);
		mDish.setText(getIntent().getExtras().getString("dish"));
		
		TextView mDescription = (TextView) findViewById(R.id.dishDescription);
		mDescription.setText(getIntent().getExtras().getString("description"));
		
		TextView mSection = (TextView) findViewById(R.id.dishSection);
		mSection.setText(getIntent().getExtras().getString("section"));
		
		TextView mPrice = (TextView) findViewById(R.id.dishPrice);
		mPrice.setText(getIntent().getExtras().getString("price"));
		
		TextView mVenue = (TextView) findViewById(R.id.venueName);
		mVenue.setText(getIntent().getExtras().getString("venue"));
		
		TextView mWebsite = (TextView) findViewById(R.id.venueWebsite);
		mWebsite.setText(getIntent().getExtras().getString("website"));
		
		TextView mPhone = (TextView) findViewById(R.id.venuePhone);
		mPhone.setText(getIntent().getExtras().getString("phone"));
		
		String geoLoc = String.format("geo:%f,%f", 37.7750, -122.4183);
		String geoAddr = getIntent().getExtras().getString("address") + "<br>" +
				 		 getIntent().getExtras().getString("locality") + ", " +
				 		 getIntent().getExtras().getString("region");
		
		TextView mFullAddress = (TextView) findViewById(R.id.venueFullAddress);
		mFullAddress.setText(Html.fromHtml("<a href=\"" + geoLoc + "\">"+ geoAddr + "</a> "));
		
		String url = getIntent().getExtras().getString("image");
		ImageView mImage = (ImageView) findViewById(R.id.dishImage);
		mImage.setImageURI(Uri.fromFile(new File(url)));
		
		this.locuid = getIntent().getExtras().getString("locuid");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dish_info, menu);
		return true;
	}
	
	public void onYesButtonClick(View view) {
		try{
		URL nearMe = new URL("http://goodplates.locu.com:8188/api/rate/?item=" + locuid + "&username=8370314&rating=like");
		nearMe.openStream();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void launchMap(View view) {
		String uri = String.format("geo:%f,%f", 37.7750, -122.4183);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		Context context = getApplicationContext();
		context.startActivity(intent);
	}
	
	public static Bitmap loadBitmap(String url) {
		try 
		{
			return  BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
			
			} 
		catch (MalformedURLException e) {
			  e.printStackTrace();
			} catch (IOException e) {
			  e.printStackTrace();
			}
		return null;
	}

}
