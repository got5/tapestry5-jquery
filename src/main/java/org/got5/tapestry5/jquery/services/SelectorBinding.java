package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;

/**
 * 
 * The selector: binding prefix, returns the jQuery selector for child component via its id.
 *
 */
public class SelectorBinding extends AbstractBinding {
	
	private final String description;
	private final String embeddedId;
	private final ComponentResources componentResources;
	
	public SelectorBinding(Location location, String description, ComponentResources componentResources, String value) {
		super(location);
		this.description = description;
		this.embeddedId = value;
		this.componentResources = componentResources;
	}

	public Object get() {
		String id = componentResources.getEmbeddedComponent(embeddedId).getComponentResources().getId();
		return String.format("#%s",id);
	}
	
	@Override
	public String toString() {
		return String.format("SelectorBinding[%s: %s]", description, embeddedId); 
	}

}
