package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.json.JSONObject;

public class CustomDatepicker {
	
	@Property
	private String value;
	
	public JSONObject getParams(){
		JSONObject json =  new JSONObject("prevText", "See Previous Month ",
							  "nextText", "See Next Month");
        return json;
	}
}
