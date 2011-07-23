package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;

public class SelectorBindingFactory implements BindingFactory{

	public Binding newBinding(String description, ComponentResources container, ComponentResources component,
		                              String expression, Location location) {
		return new SelectorBinding(location, description, component, expression);
	}

}
