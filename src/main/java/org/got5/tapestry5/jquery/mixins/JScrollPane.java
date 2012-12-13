package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.javascript.JScrollPaneStack;

@Import(stack = JScrollPaneStack.STACK_ID)
public class JScrollPane {
	/**
	 * The JSON parameter for your widget
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private JSONObject options;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject 
	private JavaScriptSupport js;
	
	void afterRender() {
		if(options == null) options = new JSONObject();
		JSONObject opt = new JSONObject();
		opt.put("id", clientElement.getClientId());
		opt.put("params", options);
		js.addInitializerCall("jscrollpane", opt);
	}
}
