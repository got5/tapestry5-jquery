package org.got5.tapestry5.jquery.mixins.ui;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;

public class Widget {
	
	@Parameter(defaultPrefix="literal")
	private String options;
	
	@Parameter(defaultPrefix="literal")
	private String name;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	@Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
	private String jqueryAlias;
	
	String widgetName() {
		if ( name != null ) {
			return name;
		}
		return this.getClass().getSimpleName().toLowerCase(); 
	}
	
	void afterRender() {
		if ( options == null ) {
			options = "";
		}
		String init = String.format("%s('#%s').%s(%s);", jqueryAlias, clientElement.getClientId(),widgetName(),options);
		javaScriptSupport.addScript(init);
	}

}
