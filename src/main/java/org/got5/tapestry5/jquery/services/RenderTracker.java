package org.got5.tapestry5.jquery.services;

import java.util.Map;

import org.apache.tapestry5.runtime.Component;

public interface RenderTracker {
	
	public void setRendering(Component component);
	public Component getRendering();
	
	public Map<String,Boolean> getIdMap();

}
