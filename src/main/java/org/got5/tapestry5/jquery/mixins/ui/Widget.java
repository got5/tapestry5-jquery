package org.got5.tapestry5.jquery.mixins.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.WidgetParams;
import org.got5.tapestry5.jquery.services.js.JSSupport;
import org.got5.tapestry5.jquery.utils.JQueryUtils;
import org.slf4j.Logger;

/**
 * Class used for creating a jQuery Widget
 * You just have to create a class extending this one. 
 * And automatically, the widget javascript method will be called. 
 * @since 2.6.0
 * 
 * @tapestrydoc
 */
public class Widget {
	
	/**
	 * The JSON parameter for your widget
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private JSONObject options;
	
	/**
	 * Use if options needs a function. Experimental
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String script;
	
	/**
	 * Name of the jQuery widget. Typically supplied by subclassing widget 
	 * to a jQuery widget name.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String widgetName;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private WidgetParams widgetParams;
	
	String widgetName() {
		if ( widgetName != null ) {
			return widgetName;
		}
		return this.getClass().getSimpleName().toLowerCase(); 
	}
	
	private JSONObject overrideParams(){
		if ( options == null ) {
			options = new JSONObject();
		}
		JSONObject params = new JSONObject();
		
		if(widgetParams.paramsForWidget(widgetName())!=null){
			params=widgetParams.paramsForWidget(widgetName());
			JQueryUtils.merge(params, options);
			return params;
		}
		 
		return options;
	}

}
