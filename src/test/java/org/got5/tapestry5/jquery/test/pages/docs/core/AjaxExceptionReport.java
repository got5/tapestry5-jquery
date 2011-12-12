package org.got5.tapestry5.jquery.test.pages.docs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.corelib.components.Zone;
import org.got5.tapestry5.jquery.utils.JQueryTabData;


public class AjaxExceptionReport {
	
	public List<JQueryTabData> getListTabData()
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
        listTabData.add(new JQueryTabData("Example","example")); 
        return listTabData;
    }
	
@Component
private Zone zone;

@OnEvent(value=EventConstants.ACTION, component="ajax")
public Object displayException(){
	
	String error = null;
	System.out.println(error.contains("error"));
	
	return zone;
}
}
