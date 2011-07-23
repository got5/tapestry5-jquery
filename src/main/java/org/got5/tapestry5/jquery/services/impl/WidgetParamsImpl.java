package org.got5.tapestry5.jquery.services.impl;

import java.util.Map;

import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.services.WidgetParams;

public class WidgetParamsImpl implements WidgetParams {
	
	public Map<String, JSONObject> widgetParams;
	
	public WidgetParamsImpl(Map<String, JSONObject> configuration){
		
		widgetParams = configuration;
		
	}
	
	public JSONObject paramsForWidget(String widget){
		return widgetParams.get(widget);
	}
	
}
