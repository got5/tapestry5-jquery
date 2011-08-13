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

/**
 * 
 * @since 2.1.2
 *
 */
public class Widget {
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private JSONObject options;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String name;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	@Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
	private String jqueryAlias;
	
	@Inject
	private WidgetParams widgetParams;
	
	@Inject
	private JSSupport jsSupport;
	
	String widgetName() {
		if ( name != null ) {
			return name;
		}
		return this.getClass().getSimpleName().toLowerCase(); 
	}
	
	void afterRender() {
		String init = String.format("%s('#%s').%s(%s);", jqueryAlias, clientElement.getClientId(),widgetName(),overrideParams());
		//javaScriptSupport.addScript(init);
		jsSupport.addScript(init);
	}
	
	private JSONObject overrideParams(){
		
		JSONObject params = new JSONObject();
		
		if(widgetParams.paramsForWidget(widgetName())!=null){
			params=widgetParams.paramsForWidget(widgetName());
			JQueryUtils.merge(params, options);
		}
		else if(options!=null) params = options;
		
		return params;
	}

}
