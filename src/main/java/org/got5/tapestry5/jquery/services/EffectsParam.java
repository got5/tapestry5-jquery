package org.got5.tapestry5.jquery.services;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.UsesConfiguration;

/**
 * Service used to define the jQuery Effects JavaScript File 
 * you want to load. 
 * 
 * @see org.got5.tapestry5.jquery.EffectsConstants
 * @since 2.6.0
 */
@UsesConfiguration(value = String.class)
public interface EffectsParam {
	
	public abstract Collection<String> getEffectsToLoad();
	
}
