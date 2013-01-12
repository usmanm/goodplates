package com.example.goodplates;



import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
		
		final AutoCompleteTextView edittext = (AutoCompleteTextView)findViewById(R.id.prefrestaurant);
		edittext.setAdapter(adapter);	
		edittext.setThreshold(2);
		
		
		mExpandableList = (ExpandableListView)findViewById(R.id.expandable_list);
		 
        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<String> arrayChildren = new ArrayList<String>();
        
      /*  Parent quiz = new Parent();
        ArrayList<String> quizfood = new ArrayList<String>();
        quiz.setTitle("Quiznos");
        quizfood.add("sandwich");
        quizfood.add("buns");
        quiz.setArrayChildren(quizfood);
        quizfood.clear();
        
        Parent dom = new Parent();
        ArrayList<String> domfood = new ArrayList<String>();        
        dom.setTitle("Dominos");
        domfood.add("pizza");
        domfood.add("soda");
        dom.setArrayChildren(domfood);
        domfood.clear();
        
        Parent olive = new Parent();
        ArrayList<String> olivefood = new ArrayList<String>();        
        olive.setTitle("Olive Garden");
        olivefood.add("noodles");
        olivefood.add("spaghetti");
        olive.setArrayChildren(olivefood);
        olivefood.clear();
        
        arrayParents.add(quiz);
        arrayParents.add(dom);
        arrayParents.add(olive);*/
        
        for (int i = 0; i < 9; i++){
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
	
	
	
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_preferences, menu);
		return true;
	}

}
