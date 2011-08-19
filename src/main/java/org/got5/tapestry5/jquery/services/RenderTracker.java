package org.got5.tapestry5.jquery.services;

import java.util.Map;

import org.apache.tapestry5.runtime.Component;

/**
 * @5.2.6
 */
public interface RenderTracker {
	
	public void push(Component component);
	public void pop();
	public Component getRendering();
	
	public Map<String,Boolean> getIdMap();

}
