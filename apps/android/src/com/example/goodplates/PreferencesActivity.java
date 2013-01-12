package com.example.goodplates;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class PreferencesActivity extends Activity {
	
	private ExpandableListView mExpandableList;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		context = getApplicationContext();
		String[] autoSuggestions =	         
				{"A16"
					,"Absinthe"
					,"Acquerello"
					,"Ad Hoc"
					,"Adesso"
					,"Amber India"
					,"Ame"
					,"AQ"
					,"Aziza"
					,"Baker & Banker"
					,"Bar Agricole"
					,"Bar Tartine"
					,"Baume"
					,"Benu"
					,"Betelnut"
					,"Bistro Aix"
					,"Bistro Don Giovanni"
					,"Bocadillos"
					,"Bottega Napa Valley"
					,"Bouchon"
					,"Boulevard"
					,"Camino"
					,"Canteen"
					,"Chapeau!"
					,"Chez Panisse"
					,"Claudine"
					,"Coco500"
					,"Coi"
					,"Commonwealth"
					,"Corso Trattoria"
					,"Cotogna"
					,"Delfina"
					,"Dopo"
					,"Dosa on Fillmore"
					,"El Paseo"
					,"Farallon"
					,"Fifth Floor"
					,"Flour + Water"
					,"Foreign Cinema"
					,"Frances"
					,"French Laundry"
					,"Gary Danko"
					,"Gialina"
					,"Greens"
					,"House of Prime Rib"
					,"Incanto"
					,"Ippuku"
					,"Koi Palace"
					,"La Ciccia"
					,"La Folie"
					,"Lers Ros"
					,"Mamacita"
					,"Manresa"
					,"Marlowe"
					,"Masa's"
					,"Mateo's Cocina Latina"
					,"Michael Mina"
					,"Mission Chinese Food"
					,"Morimoto Napa"
					,"Nojo"
					,"Nopa"
					,"Nopalito"
					,"O Chame"
					,"Oenotri"
					,"One Market"
					,"Osteria Stellina"
					,"Outerlands"
					,"Park Tavern"
					,"Perbacco"
					,"Pesce"
					,"Piccino"
					,"Piperade"
					,"Pizzaiolo"
					,"Poggio"
					,"Press"
					,"Prospect"
					,"Quince"
					,"Range"
					,"Redd"
					,"Restaurant at Meadowood"
					,"Rivoli"
					,"Saison"
					,"Sante"
					,"Scopa"
					,"Slanted Door"
					,"Solbar"
					,"Sons & Daughters"
					,"SPQR"
					,"State Bird Provisions"
					,"Sushi Ran"
					,"Swan Oyster Depot"
					,"Terra"
					,"Town Hall"
					,"Va de Vi"
					,"Waterbar"
					,"Willi's Wine Bar"
					,"Yank Sing"
					,"Zarzuela"
					,"Zuni Cafe"};
	         
		
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,autoSuggestions);
	
		final AutoCompleteTextView prefLoc = (AutoCompleteTextView)findViewById(R.id.preflocation);
		prefLoc.setText("San Fransisco, CA");
		
		final AutoCompleteTextView prefRest = (AutoCompleteTextView)findViewById(R.id.prefrestaurant);
		prefRest.setAdapter(adapter);
		
		prefRest.requestFocus();
		prefRest.setOnItemClickListener(new OnItemClickListener() { 

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		            long arg3) {
		    	
		    	InputMethodManager imm = (InputMethodManager)getSystemService(
		    		      Context.INPUT_METHOD_SERVICE);
		    		imm.hideSoftInputFromWindow(prefRest.getWindowToken(), 0);
		        ArrayList<String> menuItems = new ArrayList<String>();
		        menuItems.add("abc");
		        menuItems.add("def");
		        menuItems.add("ghi");
		        menuItems.add("jkl");
		        
		        
		    	//SHOW MENU HERE		    	
		    }
		});
		
		final Button donePrefButton = (Button) findViewById(R.id.donepref);
		donePrefButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(context, MainActivity.class);
				startActivity(i);
			}
		});
			
		}
	
	
	
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_preferences, menu);
		return true;
	}

}
