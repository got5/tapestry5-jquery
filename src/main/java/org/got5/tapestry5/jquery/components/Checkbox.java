package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;


/**
 * This mixin allows you change the look of the original checkboxes.
 * 
 * @since 2.1.1
 * @see <a href="http://access.aol.com/csun2011">http://access.aol.com/csun2011</a>
 * 
 * @tapestrydoc
 */
@ImportJQueryUI({ "jquery.ui.widget", "jquery.ui.core" })
@Import(library = { "${assets.path}/components/checkbox/jquery.ui.checkbox.js",
					"${assets.path}/components/checkbox/checkbox.js"}, 
		stylesheet = {"${assets.path}/components/checkbox/jquery.ui.checkbox.css"})
public class Checkbox extends org.apache.tapestry5.corelib.components.Checkbox{
	
	@Environmental
    private JavaScriptSupport javaScriptSupport;
    
	void afterRender()
    {
        String id = getClientId();

        JSONObject data = new JSONObject();
        
        data.put("id", id);
        
        javaScriptSupport.addInitializerCall("checkbox", data);
    }
		
}
