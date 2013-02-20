package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * 
 */
@Import(stylesheet = "${jquery.assets.root}/vendor/components/ddslick/DdSlickComponent.css")
@SupportsInformalParameters
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
	
	public String getZone(){return zone; }
	@AfterRender
	
	public void setupRender(MarkupWriter writer){
		writer.element("div", "id", getClientId(), "data-update-zone", zone);
	}
	public void afterRender(MarkupWriter writer) {
		resources.renderInformalParameters(writer);
		writer.end();
		params.put("url", resources.createEventLink(event, context).toAbsoluteURI());
		params.put("zone", zone);
		params.put("elementId", getClientId());
		javaScriptSupport.require("tjq/ddslick").with(params);
	}

	public String getClientId() {
		if (InternalUtils.isBlank(clientId))
			clientId = (InternalUtils.isNonBlank(resources.getInformalParameter("id", String.class))) ? resources
					.getInformalParameter("id", String.class) : javaScriptSupport.allocateClientId(resources);
		return clientId;
	}

}
