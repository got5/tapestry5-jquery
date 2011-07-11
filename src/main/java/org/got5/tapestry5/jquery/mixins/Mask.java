package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


/**
 * @since 2.1.1
 * @see	http://digitalbush.com/projects/masked-input-plugin/
 */
@Import(library = { "${assets.path}/mixins/mask/jquery-maskedinput.js",
					 "${assets.path}/mixins/mask/mask.js" })
public class Mask {

	@Parameter
	private String format;
	
    /**
     * The field component to which this mixin is attached.
     */
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
        
        data.put("format", format);
    
        javaScriptSupport.addInitializerCall("mask", data);
    }
}
