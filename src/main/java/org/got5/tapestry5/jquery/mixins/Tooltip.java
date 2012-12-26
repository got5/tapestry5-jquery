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
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.WidgetParams;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * @see <a href="http://jqueryui.com/tooltip/">jQuery UI Official Documentation</a>
 * @tapestrydoc
 */
public class Tooltip {
	/**
	 * The JSON parameter for your widget
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private JSONObject options;
	
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private WidgetParams widgetParams;
		
	void afterRender() {
		JSONObject json = new JSONObject();
		json.put("id", clientElement.getClientId());
		json.put("options", overrideParams());
		javaScriptSupport.require("tjq/ui").invoke("tooltip").with(json);
				
	}
	
	private JSONObject overrideParams(){
		if ( options == null ) {
			options = new JSONObject();
		}
		JSONObject params = new JSONObject();
		String widgetName = this.getClass().getSimpleName().toLowerCase();
		if(widgetParams.paramsForWidget(widgetName)!=null){
			params=widgetParams.paramsForWidget(widgetName);
			JQueryUtils.merge(params, options);
			return params;
		}
		 
		return options;
	}
}
