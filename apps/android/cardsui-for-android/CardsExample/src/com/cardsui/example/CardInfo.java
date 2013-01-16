package com.cardsui.example;

import org.json.JSONObject;

public class CardInfo {

	public String desc;
	public String dish;
	public String section;
	public String price;
	public double distance;
	public String website;
	public String venue;
	public String locality;
	public String region;
	public String address;
	public String locuid;

	public CardInfo(String de, String di, String se, String pr, double dis,
			String we, String ve, String lo, String re, String ad, String locu) {
		
		desc = de;
		dish = di;
		section = se;
		price = pr;
		distance = dis;

		website = we;
		venue = ve;
		locality = lo;
		region = re;
		address = ad;
		locuid = locu;
	}

}
