package com.cardsui.example;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jackson.map.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

public class MainActivity extends Activity {

	private CardUI mCardView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		// init CardView
		mCardView = (CardUI) findViewById(R.id.cardsview);
		mCardView.setSwipeable(true);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		ArrayList<CardInfo> allCards = new ArrayList<CardInfo>();
		// get closest restaurants
		try 
		{
			URL nearMe = new URL("http://goodplates.locu.com:8188/api/get_ranked_items/?username=8370314&page=1&size=14&lat=37.7750&lon=-122.4183&radius=10");
			BufferedReader br = new BufferedReader(new InputStreamReader(nearMe.openStream()));
			String line;
			String suggestionsJSON = "";
			while ((line = br.readLine()) != null) 
			{
				suggestionsJSON += line;
			}			
			
			allCards = new ArrayList<CardInfo>();
			JSONArray innerArray = new JSONArray(suggestionsJSON);
			for(int i = 0; i < innerArray.length(); i++)
			{
				String desc = innerArray.getJSONObject(i).getString("description");
				String dish = innerArray.getJSONObject(i).getString("title");
				String section = innerArray.getJSONObject(i).getString("section");
				String price = innerArray.getJSONObject(i).getString("price");
				double distance = innerArray.getJSONObject(i).getDouble("distance");
				String locuid = innerArray.getJSONObject(i).getString("locu_id");
				
				JSONObject ven = innerArray.getJSONObject(i).getJSONObject("venue");
				String website = ven.getString("website");
				String venue = ven.getString("name");
				String locality = ven.getString("locality");
				String region = ven.getString("region");
				String address = ven.getString("address");
				
				CardInfo ci = new CardInfo(desc,dish,section,price,distance,website,venue,locality,region,address,locuid);
				allCards.add(ci);				
			}
		} catch (Exception e) {
			int x = 1+1;
			Exception a = e;
		}

		String a = "http://distilleryimage1.s3.amazonaws.com/223434225cf911e299a722000a9d0ee0_7.jpg";
		String passthis = "";
		try{
		String dir = Environment.getExternalStorageDirectory().toString();
		OutputStream fos = null;                
		File file = new File(dir,"test.JPEG");
		Bitmap bm =BitmapFactory.decodeStream((InputStream)new URL(a).getContent());
		fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
		bos.flush();
		bos.close();
		passthis = file.getAbsolutePath();		
		}
		
		catch(Exception e){
			Exception ad = e;
		}
		
		
		for(CardInfo ci : allCards)
		{
			mCardView.addCard(new MyCard(ci.dish, ci.desc,passthis, ci.venue + ", " + ci.locality + ", " + ci.region,ci,getApplicationContext()));
		}
		
		mCardView.refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
