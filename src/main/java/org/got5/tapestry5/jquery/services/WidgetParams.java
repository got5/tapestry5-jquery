package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;
import org.apache.tapestry5.json.JSONObject;

/**
 * Provides an easy way to set a default JSONObject-type params for one of your mixin.
 * It will be use with the Widget Class.
 * 
 * @since 2.6.0
 * @author Emmanuel DEMEY
 */
@UsesMappedConfiguration(value = JSONObject.class)
public interface WidgetParams {

	JSONObject paramsForWidget(String widget);

}