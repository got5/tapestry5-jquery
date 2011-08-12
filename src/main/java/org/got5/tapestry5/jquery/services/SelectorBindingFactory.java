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
	private final RenderTracker selectorTracker;
	
	public SelectorBindingFactory(Logger logger, JavaScriptSupport javaScriptSupport,RenderTracker selectorTracker) {
		this.logger = logger;
		this.javaScriptSupport = javaScriptSupport;
		this.selectorTracker = selectorTracker;
	}

	public Binding newBinding(String description, ComponentResources container, ComponentResources component,
		                              String expression, Location location) {
		
		return new SelectorBinding(location, description, component, expression, logger, javaScriptSupport, selectorTracker);
	}

}
