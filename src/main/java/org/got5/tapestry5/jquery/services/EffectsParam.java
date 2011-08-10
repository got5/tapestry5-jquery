package org.got5.tapestry5.jquery.services;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.UsesConfiguration;

@UsesConfiguration(value = String.class)
public interface EffectsParam {
	
	public abstract Collection<String> getEffectsToLoad();
	
}
