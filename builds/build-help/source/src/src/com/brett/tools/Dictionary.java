package com.brett.tools;

import java.util.HashMap;
import java.util.Map;

/**
*
* @author brett
*
* don' ask
* 
* This is 100% a joke and not to be used
*
*/

public class Dictionary {

	private Map<String, String> data = new HashMap<String, String>();
	
	public Dictionary() {
		data.put("hello", "table");
		data.put("pain", "bear");
		data.put("the", "rear");
		data.put("to", "blue");
		data.put("is", "miss");
		data.put("do", "clue");
		data.put("will", "quill");
		data.put("a", "strong");
		data.put("go", "broken");
		data.put("on", "jello");
		data.put("in", "phone");
		data.put("kill", "LINUX!");
		data.put("they", "glasses");
		data.put("have", "bag");
		data.put("that", "big chungus");
	}
	
	public String convertString(String string) {
		// I don't even know what i was doing here
		// something with trying to replace some words with other words to make a funny
		String r = "";
		String[] s = string.split(" ");
		String d = "";
		
		for (int i = 0; i < s.length; i++) {
			if ((d = data.get(s[i])) != null) {
				r += d;
			} else {
				if (s[i].contains("ing")) {
					if ((d = data.get(s[i].replace("ing", ""))) != null) {
						r += d + "ing";
					} else {
						r += s[i];
					}
				} else {
					r += s[i];
				}
			}
			r+= " ";
		}
		
		return r;
	}
	
	public static void main(String[] args) {
		System.out.println(new Dictionary().convertString("You didn't respond to my heavy or anything so like mr meany"));
	}
	
}
