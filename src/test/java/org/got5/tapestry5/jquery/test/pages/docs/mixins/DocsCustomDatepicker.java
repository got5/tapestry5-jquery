package org.got5.tapestry5.jquery.test.pages.docs.mixins;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

public class DocsCustomDatepicker {
	
	
	public List<JQueryTabData> getListTabData()	
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
		
	    listTabData.add(new JQueryTabData("Documentation","docs"));
	    
	    listTabData.add(new JQueryTabData("Example","example"));
	    
	    return listTabData;
	}
	
	@Property
	private Date date;
	
	public JSONObject getParams(){
		return new JSONObject("nextText", "Next Month");
	}
	
	
}
