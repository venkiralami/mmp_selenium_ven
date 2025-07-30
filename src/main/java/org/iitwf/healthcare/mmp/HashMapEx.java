package org.iitwf.healthcare.mmp;


import java.util.HashMap;
import java.util.Set;

public class HashMapEx {

	public static void main(String[] args) {

		//String s = new String("Selenium");

		HashMap<String,Double> h = new HashMap<String,Double>();
		h.put("GE Power",123.45);
		h.put("Sri Havisha Hospital",2.40);
		h.put("GayatriSugars",12.97);

		System.out.println("Stock Price Details:::" + h.get("GayatriSugars"));

		Set<String> stockKeys = h.keySet();

		for(String s : stockKeys)
		{
			System.out.println("Key Name:" + s);
			System.out.println("Key Value:" + h.get(s));

		}

	}
}
