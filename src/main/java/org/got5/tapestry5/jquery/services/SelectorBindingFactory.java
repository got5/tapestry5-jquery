package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;

/**
 * @since 2.6.0
 */
public class SelectorBindingFactory implements BindingFactory {
	private final Logger logger;
	private final JavaScriptSupport javaScriptSupport;
	
	public SelectorBindingFactory(Logger logger, JavaScriptSupport javaScriptSupport) {
		this.logger = logger;
		this.javaScriptSupport = javaScriptSupport;
	}

	public Binding newBinding(String description, ComponentResources container, ComponentResources component,
		                              String expression, Location location) {
		
		return new SelectorBinding(location, description, component, expression, logger, javaScriptSupport);
	}

}
