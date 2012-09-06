package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Arnaud MALISCHEWSKI
 */
@Import(library = "${assets.path}/mixins/trigger/trigger.js")
public class Trigger {

	/**
	 * the name of the event trigger.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
	private String event;

	/**
	 * if true the event param is trigger.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
	private boolean trigger;

	/**
	 * the element which trigger the event.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
	private String element;

	/**
	 * others params for trigger event.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
	private JSONObject params;

	@InjectContainer
	private ClientElement clientElement;

	@Environmental
	private JavaScriptSupport jsSupport;

	@SetupRender
	public void init() {

	}

	@AfterRender
	public void afterRender() {
		if (trigger) {
			String elementId = getClientId();

			JSONObject specs = new JSONObject();
			specs.put("elementId", elementId);
			specs.put("event", event);
			specs.put("params", params);

			jsSupport.addInitializerCall("launchEvent", specs);
		}
	}

	public String getClientId() {
		if (element == null) {
			element = "#".concat(clientElement.getClientId());
		}
		return element;
	}

}
