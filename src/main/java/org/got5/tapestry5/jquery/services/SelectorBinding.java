package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;

/**
 * 
 * The selector: binding prefix, returns the jQuery selector for child component via its id.
 *
 */
public class SelectorBinding extends AbstractBinding {
	
	private final String description;
	private final String tid;
	private final ComponentResources componentResources;
	private final Logger logger;
	private final JavaScriptSupport javaScriptSupport;
	
	
	public SelectorBinding(Location location, String description, ComponentResources componentResources, String value, Logger logger, JavaScriptSupport javaScriptSupport) {
		super(location);
		this.description = description;
		this.tid = value;
		this.componentResources = componentResources;
		this.logger = logger;
		this.javaScriptSupport = javaScriptSupport;
	}

	public Object get() {	
		Component c = componentResources.getEmbeddedComponent(tid);
		String id = null;
		if ( ClientElement.class.isAssignableFrom(c.getClass())) {
			ClientElement ce = (ClientElement) c;
			id = ce.getClientId();
		}
		
		if ( id != null ) {
			return String.format("jQuery('#%s')",id);
		}
		//String.format("$('td[name=tcol1]') ",tid);
		return String.format("jQuery(selector%s)",tid);
	}
	
	@Override
	public String toString() {
		return String.format("SelectorBinding[%s: %s]", description, tid); 
	}
	
	@Override
	public boolean isInvariant() {
		return false;
	}
	

}
