package org.got5.tapestry5.jquery.mixins;


/**
 * This mixin allows you to display a tooltip for all your inputs. 
 * It use the title attribute of your element.
 * 
 * @since 2.1.1
 * @see <a href="http://access.aol.com/csun2011">http://access.aol.com/csun2011</a>
 */
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Import;
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
 * @tapestrydoc
 */

@Import(stylesheet = { "${assets.path}/mixins/tooltip/jquery.ui.tooltip.css" })
public class Tooltip {
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
	@Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
	private String jqueryAlias;
	
	@Inject
	private WidgetParams widgetParams;
	
	@Inject
	private JSSupport jsSupport;
	
	@SuppressWarnings("unused")
    @Inject
	private Logger logger;
	
	String widgetName() {
		if ( widgetName != null ) {
			return widgetName;
		}
		return this.getClass().getSimpleName().toLowerCase(); 
	}
	
	void afterRender() {
		JSONObject json = new JSONObject();
		json.put("id", clientElement.getClientId());
		json.put("options", overrideParams());
		javaScriptSupport.require("tjq/tooltip").with(json);
				
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
