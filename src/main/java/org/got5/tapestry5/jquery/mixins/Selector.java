package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.RenderTracker;

/*
 * Added to every ClientId component via injection. 
 */
public class Selector {
	
	@InjectContainer
	private Component component;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	private RenderTracker renderTracker;
	
	
	void beginRender() {
		renderTracker.push(resources.getContainer());
	}
	
	void afterRender() {
		renderTracker.pop();
		String id = resources.getId();
		if ( renderTracker.getIdMap().containsKey(id) && ClientElement.class.isAssignableFrom( component.getClass())) {
			ClientElement clientElement = (ClientElement) component;
			javaScriptSupport.addScript(InitializationPriority.EARLY,"selector['%s'] = '#%s';", id,clientElement.getClientId());
		}
	}

}
