package org.got5.tapestry5.jquery.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.runtime.Component;
import org.got5.tapestry5.jquery.services.RenderTracker;

/**
 * @since 5.2.6
 */
@Scope(ScopeConstants.PERTHREAD)
public class RenderTrackerImpl implements RenderTracker {
	private Stack<Component> components = new Stack<Component>();
	private Map<String, Boolean> ids = new HashMap<String, Boolean>();

	public void push(Component component) {
		components.push(component);
	}
	
	public void pop() {
		components.pop();
	}

	public Component getRendering() {
		if ( components.size() > 0) {
			return components.peek();
		}
		return null;
	}

	public Map<String, Boolean> getIdMap() {
		return ids;
	}

}
