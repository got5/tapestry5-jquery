package org.got5.tapestry5.jquery.test.pages.test;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class Form {
	
	@Persist
	@Property
	private String submit;
	
	@Inject
	private Request request;
	
	@Component(id = "zone")
	private org.apache.tapestry5.corelib.components.Zone formZone;
	
	public Object onSuccess(){
		submit = request.getParameter("t:submit");
		return formZone;
	}
}
