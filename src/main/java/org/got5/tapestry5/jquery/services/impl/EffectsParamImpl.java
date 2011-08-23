package org.got5.tapestry5.jquery.services.impl;

import java.util.Collection;

import org.got5.tapestry5.jquery.services.EffectsParam;

/**
 * Principal Implementation of the EffectParam Service
 * Used to define the JavaScript files you want load, 
 * in order to use the jQuery Effects.
 * 
 * @since 2.6.0
 */
public class EffectsParamImpl implements EffectsParam {
	
	public Collection<String> effectToLoad;
	
	public EffectsParamImpl(Collection<String> effectToLoad) {
		super();
		this.effectToLoad = effectToLoad;
	}

	public Collection<String> getEffectsToLoad() {
		return effectToLoad;
	}

}
