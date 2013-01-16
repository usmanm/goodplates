package com.cardsui.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class MyCard extends Card {
	public CardInfo card;
	public String uri;
	public MyCard(String title,String desc, String url, String vendor, CardInfo ci, final Context c){
		super(title,desc,url,vendor);
		this.card = ci;
		this.uri = url;
		this.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	Intent i = new Intent(c, DishInfoActivity.class);
		    	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    	Bundle thomas = new Bundle();
		    	
		    	thomas.putString("dish",card.dish);
				thomas.putString("description",card.desc);
				thomas.putString("section",card.section);
				thomas.putString("price",card.price);
				thomas.putString("venue",card.venue);
				thomas.putString("website",card.website);
				thomas.putDouble("distance",card.distance);
				
				thomas.putString("phone","(123)456-7890");
				
				thomas.putString("address",card.address);
				thomas.putString("locality",card.locality);
				thomas.putString("region",card.region);
				thomas.putString("image",uri);
				
				thomas.putString("locuid", card.locuid);
		    	
				i.putExtras(thomas);
				
				c.startActivity(i);
		      }
		    }); 
		
		
	}

	@Override
	public View getCardContent(Context context) {
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		View view = LayoutInflater.from(context).inflate(R.layout.card_ex, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.vendor)).setText(vendor);
		((TextView) view.findViewById(R.id.description)).setText(desc);		
		((ImageView) view.findViewById(R.id.imgview)).setImageURI(Uri.fromFile(new File(url)));
		((ImageView) view.findViewById(R.id.imgview)).setScaleType(ScaleType.FIT_XY);
		
		return view;
	}	
	
	/*public static Bitmap loadBitmap(String url) {
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
	}*/
	
}
