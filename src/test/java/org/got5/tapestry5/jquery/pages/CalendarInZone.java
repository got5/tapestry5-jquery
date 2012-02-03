package org.got5.tapestry5.jquery.pages;

import java.util.Date;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

public class CalendarInZone {
	
	@Property
	private Date date;
	
	@InjectComponent
	private Zone zone;
	
	@OnEvent(EventConstants.ACTION)
	public Object updateZone(){
		return zone.getBody();
	}
}