package org.got5.tapestry5.jquery.test.pages.test;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class AjaxLink {

	@InjectComponent
	private org.apache.tapestry5.corelib.components.Zone myZone;
	@Persist
	@Property
	private int count;
	public Object onAction(){
		count++;
		return myZone.getBody();
	}
}
