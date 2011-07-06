package org.got5.tapestry5.jquery.test.pages.test;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.json.JSONObject;

public class CustomDatepicker {
	@Property
	private String value;
	
	public JSONObject getParams(){
		return new JSONObject("prevText", "Previous Month ",
							  "nextText", "Next Month");
	}
}
