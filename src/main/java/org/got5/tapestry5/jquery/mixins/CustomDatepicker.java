package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = { "${assets.path}/mixins/customDatepicker/customDatepicker.js" })
public class CustomDatepicker {
	
	@Parameter
	private JSONObject params;
	
	@InjectContainer
    private ClientElement element;
	
	@Environmental
    private JavaScriptSupport javaScriptSupport;
	
	 /**
     * Mixin afterRender phrase occurs after the component itself. 
     * @param writer
     */
    void afterRender(MarkupWriter writer)
    {
        	
        String id = element.getClientId();

        JSONObject data = new JSONObject();
    
        data.put("id", id);
        
        data.put("params", params);
    
        javaScriptSupport.addInitializerCall(InitializationPriority.LATE,"customDatepicker", data);
    }
	
}
