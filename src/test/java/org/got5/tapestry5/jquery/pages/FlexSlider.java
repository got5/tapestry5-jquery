package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.json.JSONObject;

public class FlexSlider {
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("animation", "slide");
		json.put("animationLoop", false);
		json.put("itemWidth", 210);
		json.put("itemMargin", 5);
		return json;
	}
}
