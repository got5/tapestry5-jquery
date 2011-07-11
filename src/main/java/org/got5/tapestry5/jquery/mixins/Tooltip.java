package org.got5.tapestry5.jquery.mixins;


/**
 * @since 2.1.1
 * @see http://access.aol.com/csun2011
 */
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

@ImportJQueryUI({ "jquery.ui.widget", "jquery.ui.core" })
@Import(library = { "${assets.path}/mixins/tooltip/jquery.ui.tooltip.js", 
					"${assets.path}/mixins/tooltip/tooltip.js" }, 
		stylesheet = { "${assets.path}/mixins/tooltip/jquery.ui.tooltip.css" })
public class Tooltip {
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
        
        javaScriptSupport.addInitializerCall("tooltip", data);
    }
}
