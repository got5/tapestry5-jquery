package org.got5.tapestry5.jquery.services.impl;

import java.util.Collection;

import org.got5.tapestry5.jquery.services.EffectsParam;

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
