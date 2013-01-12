package com.example.goodplates;



import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;

public class PreferencesActivity extends Activity {
	
	private ExpandableListView mExpandableList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		String[] autoSuggestions =
	         {
	         "C",
	         "C++",
	         "Java",
	         "C#.NET",
	         "iPhone",
	         "Android",
	         "ASP.NET",
	         "PHP",
	         "Python",
	         };
		
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,autoSuggestions);
	
		final AutoCompleteTextView prefLoc = (AutoCompleteTextView)findViewById(R.id.preflocation);
		prefLoc.setText("San Fransisco, CA");
		
		final AutoCompleteTextView prefRest = (AutoCompleteTextView)findViewById(R.id.prefrestaurant);
		prefRest.setAdapter(adapter);	
		prefRest.setThreshold(2);
		prefRest.requestFocus();
		
        final Button prefSearch = (Button) findViewById(R.id.prefsearch);
        prefSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				//hide keyboard on button click
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
				
				mExpandableList = (ExpandableListView)findViewById(R.id.expandable_list);
				 
		        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
		        ArrayList<String> arrayChildren = new ArrayList<String>();
				for (int i = 0; i < 4; i++){
		            //for each "i" create a new Parent object to set the title and the children
		            Parent parent = new Parent();
		            parent.setTitle("Restaurant " + i);
		            arrayChildren.add("Menu Item " + i);
		            parent.setArrayChildren(arrayChildren);
		 
		            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
		            arrayParents.add(parent);
		        }
		 
		        //sets the adapter that provides data to the list.
		        mExpandableList.setAdapter(new MyCustomAdapter(PreferencesActivity.this,arrayParents));
				
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
