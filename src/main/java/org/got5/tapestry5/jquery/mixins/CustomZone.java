package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.WidgetParams;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * This mixin allows you to override the default configuration parameters of the Datepicker
 * 
 * @since 2.1.2
 * @see <a href="http://jqueryui.com/demos/datepicker">http://jqueryui.com/demos/datepicker</a>
 */

public class CustomZone {
	
	/**
	 * The DatePicker parameters you want to override.
	 */
	@Parameter
	private JSONObject params;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String selector;
	
	@Property
	private JSONObject defaultParamsObject;
	
	@InjectContainer
    private ClientElement element;
	
	@Environmental
    private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private WidgetParams widgetParams;
	
	@Property
	private String theSelector;
	
	void setupRender(){
		defaultParamsObject = widgetParams.paramsForWidget(this.getClass().getSimpleName().toLowerCase());
	}
	 /**
     * Mixin afterRender phrase occurs after the component itself. 
     * @param writer
     */
    void afterRender(MarkupWriter writer)
    {
    	theSelector = selector;
		if(InternalUtils.isBlank(theSelector)) theSelector="#" + element.getClientId();
	


        if(defaultParamsObject!=null) JQueryUtils.merge(defaultParamsObject,params);
        else defaultParamsObject = params;

        javaScriptSupport.addScript(InitializationPriority.LATE,"$('%s').tapestryZone({opt: %s});", theSelector, defaultParamsObject);
    }
}
