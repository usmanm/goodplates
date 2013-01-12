package com.cardsui.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class MyCard extends Card {

	public MyCard(String title,String desc, String url){
		super(title,desc,url);
	}

	@Override
	public View getCardContent(Context context) {
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		View view = LayoutInflater.from(context).inflate(R.layout.card_ex, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.description)).setText(desc);		
		((ImageView) view.findViewById(R.id.imgview)).setImageBitmap(loadBitmap(url));
		
		return view;
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
