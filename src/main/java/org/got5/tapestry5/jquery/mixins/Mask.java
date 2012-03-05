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
 * It used to specify the disired format of your input. Possible values :
 *	- '9' for [0-9]
 *	- 'a' for [A-Za-z]
 *	- '*' for [A-Za-z0-9]
 *
 * @since 2.1.1
 * @see	<a href="http://digitalbush.com/projects/masked-input-plugin/">http://digitalbush.com/projects/masked-input-plugin/</a>
 * 
 * @tapestrydoc
 */
@Import(library = { "${assets.path}/mixins/mask/jquery-maskedinput.js",
					 "${assets.path}/mixins/mask/mask.js" })
public class Mask {

	/**
	 * The format you want to use for your input.
	 */
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

        javaScriptSupport.addInitializerCall("mask", new JSONObject("id", id, "format", format));
    }
}
