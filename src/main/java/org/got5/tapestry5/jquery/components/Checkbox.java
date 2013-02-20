package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


/**
 * This mixin allows you change the look of the original checkboxes.
 * 
 * @since 2.1.1
 * @see <a href="http://access.aol.com/csun2011">http://access.aol.com/csun2011</a>
 * 
 * @tapestrydoc
 */
@Import(stylesheet = {"${jquery.assets.root}/vendor/components/checkbox/jquery.ui.checkbox.css"})
public class Checkbox extends org.apache.tapestry5.corelib.components.Checkbox{
	
	@Environmental
    private JavaScriptSupport javaScriptSupport;
    
	void afterRender()
    {
        JSONObject data = new JSONObject();
        
        data.put("id", getClientId());
        

        javaScriptSupport.require("tjq/checkbox").with(data);
    }
		
}
