package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.slf4j.Logger;

/**
 * @since 2.6.0
 */
public class SelectorBindingFactory implements BindingFactory {
	private final RenderTracker selectorTracker;
	private final String alias;
	
	public SelectorBindingFactory(Logger logger, JavaScriptSupport javaScriptSupport,RenderTracker selectorTracker,
			@Symbol(JQuerySymbolConstants.JQUERY_ALIAS) String alias) {
		this.selectorTracker = selectorTracker;
		this.alias = alias;
	}

	public Binding newBinding(String description, ComponentResources container, ComponentResources component,
		                              String expression, Location location) {
		
		return new SelectorBinding(location, description, component, expression, selectorTracker, alias);
	}

}
