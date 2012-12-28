package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.javascript.DDSlickStack;

/**
 * 
 */
@Import(stack = DDSlickStack.STACK_ID)
public class DdSlick implements ClientElement {

	// parameters

	@Parameter(required = true)
	private JSONObject params;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
	private String event;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String zone;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Parameter
	private Object[] context;

	// injection

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private ComponentResources resources;

	@SetupRender
	public void init() {

	}

	@AfterRender
	public void afterRender() {
		String url = resources.createEventLink(event, context).toAbsoluteURI();
		params.put("url", url);
		params.put("zone", zone);
		params.put("elementId", getClientId());
		// javaScriptSupport.addScript("new initDdSlickComponent(%s)", params.toString());
		javaScriptSupport.addInitializerCall("initDdSlickComponent", params);
	}

	public String getClientId() {
		if (InternalUtils.isBlank(clientId))
			clientId = (InternalUtils.isNonBlank(resources.getInformalParameter("id", String.class))) ? resources
					.getInformalParameter("id", String.class) : javaScriptSupport.allocateClientId(resources);
		return clientId;
	}

}
