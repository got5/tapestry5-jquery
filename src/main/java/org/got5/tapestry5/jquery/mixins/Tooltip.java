package org.got5.tapestry5.jquery.mixins;


import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.mixins.ui.Widget;

/**
 * This mixin allows you to display a tooltip for all your inputs. 
 * It use the title attribute of your element.
 * 
 * You can specify the title in a properties file of your component/page using this mixin. 
 * You just need to specify a value for a key : [client-id]-title 
 * 
 * 
 * @since 2.1.1
 * @see <a href="http://access.aol.com/csun2011">http://access.aol.com/csun2011</a>
 *
 * @tapestrydoc
 */
@ImportJQueryUI({ "jquery.ui.widget", "jquery.ui.core" })
@Import(library = { "${assets.path}/mixins/tooltip/jquery.ui.tooltip.js" }, 
		stylesheet = { "${assets.path}/mixins/tooltip/jquery.ui.tooltip.css" })
@MixinAfter
public class Tooltip extends Widget{
	
	@InjectContainer
	private ClientElement el;
	
	@Inject
	private ComponentResources cr;
	
	private Element element;

    void beginRender(MarkupWriter writer)
    {
    	element = writer.getElement();
    }
    
    @AfterRender
    void overrideTitle()
    {	
    	if (InternalUtils.isBlank(element.getAttribute("title")))
        {
            element.attribute("title", cr.getContainerResources().
            		getContainerMessages().get(String.format("%s-title",el.getClientId())));
        } 
    }
}
