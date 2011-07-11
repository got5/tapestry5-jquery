package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryComponentConstants;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * This mixin allows you to override the default configuration parameters of the Datepicker
 * 
 * @since 2.1.1
 * @see <a href="http://jqueryui.com/demos/datepicker">http://jqueryui.com/demos/datepicker</a>
 */
@Import(library = { "${assets.path}/mixins/customDatepicker/customDatepicker.js" })
public class CustomDatepicker {
	
	/**
	 * The DatePicker parameters you want to override.
	 */
	@Parameter
	private JSONObject params;
	
	@Property
	private JSONObject defaultParamsObject;
	
	@InjectContainer
    private ClientElement element;
	
	@Environmental
    private JavaScriptSupport javaScriptSupport;
	
	@Inject
	@Symbol(value = JQueryComponentConstants.CUSTOM_DATEPICKER_PARAMS)
	private String defaultParamsString;
	
	void setupRender(){
		
		defaultParamsObject = new JSONObject(defaultParamsString);
		
	}
	 /**
     * Mixin afterRender phrase occurs after the component itself. 
     * @param writer
     */
    void afterRender(MarkupWriter writer)
    {
        	
        String id = element.getClientId();

        JSONObject data = new JSONObject();
    
        data.put("id", id);
        
        JQueryUtils.merge(defaultParamsObject,params);
        
        data.put("params", defaultParamsObject);
        
        javaScriptSupport.addInitializerCall(InitializationPriority.LATE,"customDatepicker", data);
    }
}
