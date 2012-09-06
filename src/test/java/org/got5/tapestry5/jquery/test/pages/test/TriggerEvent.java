package org.got5.tapestry5.jquery.test.pages.test;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

/**
 * @author Arnaud MALISCHEWSKI
 */
public class TriggerEvent {

	@Property
	private boolean trigger;

	@Persist
	@Property
	private int number;

	@Property
	@InjectComponent(value = "buttonZone")
	private Zone buttonZone;

	@Property
	@InjectComponent(value = "myZone")
	private Zone myZone;

	@Inject
	private Request request;

	@OnEvent(EventConstants.ACTIVATE)
	public void onActivate() {

	}

	@SetupRender
	private void setupRender() {

	}

	@OnEvent(value = EventConstants.ACTION, component = "launchEvent")
	public Object onButtonClick() {
		trigger = true;
		return request.isXHR() ? buttonZone.getBody() : null;
	}

	@OnEvent(value = "eventAfterReceiveEventGo")
	public Object onRefreshMyZone(@RequestParameter(value = "qte", allowBlank = true) String qte) {
		// String qte = request.getParameter("qte");
		String ref = request.getParameter("ref");
		System.out.println("qte------------->" + qte);
		System.out.println("ref------------->" + ref);
		number++;
		return request.isXHR() ? myZone.getBody() : null;
	}

	public JSONObject getParams() {
		JSONObject options = new JSONObject();
		options.put("ref", "produit12345");
		options.put("qte", "5");
		return options;
	}

}
