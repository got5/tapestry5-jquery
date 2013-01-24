package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class Bind {
	
	@Component
	@Property
	private org.apache.tapestry5.corelib.components.Zone clickZone;
	
	@Property
	@Persist
	private String message;
	
	public void setupRender(){
		message = "Bind test";
	}
	
	@OnEvent(value="click")
	Object onClick() {
		System.out.println("############################");
		message = "click OK";
		return clickZone.getBody();
	}

}
