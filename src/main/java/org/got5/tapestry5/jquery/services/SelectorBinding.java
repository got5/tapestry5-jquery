package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.runtime.Component;
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
	private final RenderTracker renderTracker;
	
	
	public SelectorBinding(Location location, String description, ComponentResources componentResources, 
			String value, Logger logger, JavaScriptSupport javaScriptSupport, RenderTracker selectorTracker) {
		super(location);
		this.description = description;
		this.tid = value;
		this.componentResources = componentResources;
		this.logger = logger;
		this.javaScriptSupport = javaScriptSupport;
		this.renderTracker = selectorTracker;
	}

	public Object get() {	
		Component c = null;
		if ( "this".equals(tid)) {
			c = renderTracker.getRendering();
		} else {
			c = componentResources.getEmbeddedComponent(tid);
		}
		if ( c == null ) {
			throw new IllegalArgumentException("can't find id for selector binding:" + tid);
		}
		String id = null;
		if ( ClientElement.class.isAssignableFrom(c.getClass())) {
			ClientElement ce = (ClientElement) c;
			try {
				id = ce.getClientId();
			} catch (IllegalStateException e) {
				//It's OK if we can't get the client id now
			}
		}
		
		if ( id != null ) {
			return String.format("jQuery('#%s')",id);
		}
		renderTracker.getIdMap().put(tid, true);
		return String.format("jQuery(selector['%s'])",tid);
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
