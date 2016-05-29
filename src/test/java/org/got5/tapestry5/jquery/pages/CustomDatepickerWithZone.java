package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.ClientBodyElement;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.LocalDate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class CustomDatepickerWithZone {
	
	 @Property
	 private LocalDate someDate;
	 
	 @Inject
	 private AjaxResponseRenderer ajax;
	 
	 @InjectComponent
	 private ClientBodyElement zone;
	 
	 @Property
	 private String value;

	 public JSONObject getNextParams() {
	        JSONObject params = new JSONObject();
	        params.put("dateFormat", "yy, d MM, DD");
	        params.put("changeMonth", true);
	        params.put("changeYear", true);
	        return params;
	    }

	 void onRerender() {
	        ajax.addRender(zone);
	    }
	
}
