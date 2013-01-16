package com.fima.cardsui.objects;

import android.content.Context;
import android.view.View;

public abstract class AbstractCard {

	protected String title;
	protected String desc;
	protected String url;
	protected String vendor;
	
	public abstract View getView(Context context);
	
	public abstract View getView(Context context, boolean swipable);
	
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getVendor() {
		return vendor;
	}
	
}
