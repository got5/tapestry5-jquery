package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;

public class SelectorBindingFactory implements BindingFactory {
	private final Logger logger;
	private final JavaScriptSupport javaScriptSupport;
	
	public SelectorBindingFactory(Logger logger, JavaScriptSupport javaScriptSupport) {
		this.logger = logger;
		this.javaScriptSupport = javaScriptSupport;
	}

	public Binding newBinding(String description, ComponentResources container, ComponentResources component,
		                              String expression, Location location) {
		
		//String id = id(component,expression);
		logger.info("new binding {}",expression);
		return new SelectorBinding(location, description, component, expression, logger, javaScriptSupport);
	}
	
	/*
	String id(ComponentResources component, String expression) {
		Component c = component.getEmbeddedComponent(expression);
		String id = null;
		if ( ClientElement.class.isAssignableFrom(c.getClass())) {
			ClientElement ce = (ClientElement) c;
			id = ce.getClientId();
		} else {
			id = c.getComponentResources().getId();
		}
		return id;
	}
	*/

}
