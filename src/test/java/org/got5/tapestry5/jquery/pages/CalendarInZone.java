package org.got5.tapestry5.jquery.pages;

import java.util.Date;
import java.util.Locale;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;

public class CalendarInZone {
	
	@Property
	private Date date;
	
	@InjectComponent
	private Zone zone;
	
	@Property
	private boolean showContents;
	
	@Inject
    private PersistentLocale persistentLocale;
	
	@OnEvent(value = "action", component = "link")
	public Object updateZone(){
		showContents = true;
		return zone.getBody();
	}
	
	void onActionFromEnglish() { persistentLocale.set(Locale.ENGLISH); }

	Object onActionFromFrench() { persistentLocale.set(Locale.FRENCH);showContents = true;
	return zone.getBody(); }
}