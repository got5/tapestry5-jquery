package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A simple mixin for attaching javascript that updates a zone on any client-side event.
 * Based on http://tinybits.blogspot.com/2010/03/new-and-better-zoneupdater.html
 */
@Import(library = "${assets.path}/mixins/zoneupdater/zoneupdater.js")
public class ZoneUpdater {

	/**
	 * The event to listen for on the client. If not specified, zone update can
	 * only be triggered manually through calling updateZone on the JS object.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String clientEvent;

	/**
	 * The event to listen for in your component class
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
	private String event;

	/**
	 * 
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "default")
	private String prefix;
	
	/**
	 * The context of the Ajax request
	 */
	@Parameter
	private Object[] context;

	/**
	 * The zone to be updated by us.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
	private String zone;

	/**
	 * The element we attach ourselves to
	 */
	@InjectContainer
	private ClientElement element;

	@Inject
	private ComponentResources resources;

	@Environmental
	private JavaScriptSupport jsSupport;

	@AfterRender
	void initJavaScript() {

		JSONObject spec = new JSONObject();
		
		spec.put("url", resources.createEventLink(event, context).toURI());
		spec.put("elementId", element.getClientId());
		spec.put("event", clientEvent);
		spec.put("zone", zone);

		jsSupport.addScript("%sZoneUpdater = new T5JQZoneUpdater(%s)", prefix,
				spec.toString());
	}
}