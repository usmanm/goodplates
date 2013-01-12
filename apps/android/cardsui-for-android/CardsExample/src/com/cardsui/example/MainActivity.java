package com.cardsui.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

public class MainActivity extends Activity {

	private CardUI mCardView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);

		// init CardView
		mCardView = (CardUI) findViewById(R.id.cardsview);
		mCardView.setSwipeable(true);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		
		String a = "http://dev.tovpizza.com/wp-capalon/uploads/2012/03/pizza21.jpg";
		String b = "http://2.bp.blogspot.com/-RJmAVnh-Rjs/UFCDLA6lKEI/AAAAAAAAAO8/Tw3s4iUFpe4/s1600/tuna-salad-1.jpg";
		String c = "http://roughwriter.yc.edu/wp-content/uploads/2011/05/macaroni.jpg";
		String d = "http://www.simplyrecipes.com/wp-content/uploads/2008/04/spaghetti-meatballs.jpg?ea6e46";
		String e = "http://howtomakefrenchtoastinfo.com/wp-content/uploads/2012/06/french-toast.jpg";		

		// add AndroidViews Cards
		mCardView.addCard(new MyCard("Pizza - Pizza Hut","ahmed is the pizza bitch\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus odio eu metus auctor vulputate. Vivamus aliquet, orci vel semper luctus, nibh orci rutrum nisi, vel suscipit eros sapien a nibh. Sed tempor pulvinar leo non eleifend. Aenean sem quam, pulvinar non porta ac, dictum et urna. Suspendisse ac lacinia justo. Curabitur sollicitudin ultricies suscipit. Vivamus at leo vel tortor iaculis congue. Duis eleifend orci id nisi commodo vestibulum.",a));		
		MyCard androidViewsCard = new MyCard("Tuna Salad - Fresh Choice","i really need to get some sleep\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus odio eu metus auctor vulputate. Vivamus aliquet, orci vel semper luctus, nibh orci rutrum nisi, vel suscipit eros sapien a nibh. Sed tempor pulvinar leo non eleifend. Aenean sem quam, pulvinar non porta ac, dictum et urna. Suspendisse ac lacinia justo. Curabitur sollicitudin ultricies suscipit. Vivamus at leo vel tortor iaculis congue. Duis eleifend orci id nisi commodo vestibulum.",b);
		
		
		androidViewsCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.androidviews.net/"));
				startActivity(intent);

			}
		});
		mCardView.addCard(androidViewsCard);

		// add one card, and then add another one to the last stack.
		mCardView.addCard(new MyCard("Mac and Cheese - Dino's Kitchen","yummy\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus odio eu metus auctor vulputate. Vivamus aliquet, orci vel semper luctus, nibh orci rutrum nisi, vel suscipit eros sapien a nibh. Sed tempor pulvinar leo non eleifend. Aenean sem quam, pulvinar non porta ac, dictum et urna. Suspendisse ac lacinia justo. Curabitur sollicitudin ultricies suscipit. Vivamus at leo vel tortor iaculis congue. Duis eleifend orci id nisi commodo vestibulum.",c));
		mCardView.addCard(new MyCard("Spaghetti - Fat Sal's","the quick brown fox jumped over the lazy dog\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus odio eu metus auctor vulputate. Vivamus aliquet, orci vel semper luctus, nibh orci rutrum nisi, vel suscipit eros sapien a nibh. Sed tempor pulvinar leo non eleifend. Aenean sem quam, pulvinar non porta ac, dictum et urna. Suspendisse ac lacinia justo. Curabitur sollicitudin ultricies suscipit. Vivamus at leo vel tortor iaculis congue. Duis eleifend orci id nisi commodo vestibulum.",d));

		// add one card
		mCardView.addCard(new MyCard("French Toast - Hometown Buffet","i like turtles\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus cursus odio eu metus auctor vulputate. Vivamus aliquet, orci vel semper luctus, nibh orci rutrum nisi, vel suscipit eros sapien a nibh. Sed tempor pulvinar leo non eleifend. Aenean sem quam, pulvinar non porta ac, dictum et urna. Suspendisse ac lacinia justo. Curabitur sollicitudin ultricies suscipit. Vivamus at leo vel tortor iaculis congue. Duis eleifend orci id nisi commodo vestibulum.",e));

		// create a stack
		/*CardStack stack = new CardStack();
		stack.setTitle("title test");

		// add 3 cards to stack
		stack.add(new MyCard("3 cards"));
		stack.add(new MyCard("3 cards"));
		stack.add(new MyCard("3 cards"));

		// add stack to cardView
		mCardView.addStack(stack);*/

		// draw cards
		mCardView.refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
