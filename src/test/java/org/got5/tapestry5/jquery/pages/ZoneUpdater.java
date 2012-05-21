package org.got5.tapestry5.jquery.pages;

import java.util.Date;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class ZoneUpdater {

	@Inject
	private Request request;


	@InjectComponent
	private Zone nameZone;


	@Property
	@Persist
	private String firstName;
	@Property
	@Persist
	private String lastName;


	void setupRender() {
		if (firstName == null && lastName == null) {
			firstName = "Humpty";
			lastName = "Dumpty";
		}
	}

	Object onFirstNameChanged() {
		firstName = request.getParameter("param");
		if (firstName == null) {
			firstName = "";
		}
		return request.isXHR() ? nameZone.getBody() : null;
	}

	Object onLastNameChanged() {
		lastName = request.getParameter("param");
		if (lastName == null) {
			lastName = "";
		}
		return request.isXHR() ? nameZone.getBody() : null;
	}


	public String getName() {
		return firstName + " " + lastName;
	}

	public Date getTime() {
		return new Date();
	}
}