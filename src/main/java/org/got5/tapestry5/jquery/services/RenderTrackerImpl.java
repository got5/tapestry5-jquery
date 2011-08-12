package org.got5.tapestry5.jquery.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.runtime.Component;

@Scope(ScopeConstants.PERTHREAD)
public class RenderTrackerImpl implements RenderTracker {
	private Component component;
	private Map<String, Boolean> ids = new HashMap<String, Boolean>();

	public void setRendering(Component component) {
		this.component = component;
	}

	public Component getRendering() {
		return component;
	}

	public Map<String, Boolean> getIdMap() {
		return ids;
	}

}
