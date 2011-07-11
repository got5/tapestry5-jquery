package org.got5.tapestry5.jquery.test.pages.docs.mixins;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

public class DocsCustomDatepicker {
	
	
	@Property
	private Date date;
	
	@Property
	private List<JQueryTabData> listTabData;
	 
	@SetupRender
	void onSetupRender()
	{
		listTabData = new ArrayList<JQueryTabData>();
	    listTabData.add(new JQueryTabData("Documentation","docs"));
	    listTabData.add(new JQueryTabData("Example","example"));
	}
	
	public JSONObject getParams(){
		return new JSONObject(
							  "nextText", "Next Month");
	}
	
	
}
