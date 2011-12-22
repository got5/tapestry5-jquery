package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class Bind {
	
	@InjectComponent
	@Property
	private org.apache.tapestry5.corelib.components.Zone clickZone;
	
	@Inject
	private Logger logger;
	
	@OnEvent(value="click")
	Object onClick() {
		return clickZone.getBody();
	}

}
