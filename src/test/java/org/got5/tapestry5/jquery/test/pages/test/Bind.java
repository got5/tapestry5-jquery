package org.got5.tapestry5.jquery.test.pages.test;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class Bind {
	
	@Component
	private Zone clickZone;
	
	@Inject
	private Logger logger;
	
	@OnEvent(value="click")
	Object onClick() {
		logger.info("zone clicked");
		return clickZone;
	}

}
