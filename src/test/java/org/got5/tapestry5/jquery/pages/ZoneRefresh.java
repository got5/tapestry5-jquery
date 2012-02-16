package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.got5.tapestry5.jquery.JQueryEventConstants;

public class ZoneRefresh {
	
	@Property
	@Persist
	private int i;
	
	@OnEvent(JQueryEventConstants.REFRESH)
	public void refesh(){
		i++;
	}
}