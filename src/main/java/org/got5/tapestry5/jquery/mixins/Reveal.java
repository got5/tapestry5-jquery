package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


/**
 * This mixin allows you to display a modal window.
 * 
 * @since 2.1.1
 * @see <a href="http://www.zurb.com/playground/reveal-modal-plugin">http://www.zurb.com/playground/reveal-modal-plugin</a>
 * 
 * @tapestrydoc
 */
@Import(library = {"${assets.path}/mixins/reveal/jquery.reveal.js","${assets.path}/mixins/reveal/reveal.js"},stylesheet={"${assets.path}/mixins/reveal/reveal.css"})
public class Reveal{
	
	/**
	 * The id of the div you want to display.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String div;
	
	/**
	 * The effect you want to use. Possible parameter values : fade, fadeAndPop, none
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL,
				value="fadeAndPop")
	private String animation;
	
	/**
	 *  How fast your animation has to be.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL,
			value="300")
	private Integer animationspeed;
	
	/**
	 * Can we close the model window by clicking the background?
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL,
				value="true")
	private boolean closeonbackgroundclick;
	
	/**
	 * The CSS class for the element that will close the modal.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL,
				value="close-reveal-modal")
	private String dismissmodalclass;
	
	@InjectContainer 
	private ClientElement element;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
		
	@AfterRender
	public void finish(MarkupWriter w){
		
		JSONObject jso = new JSONObject();
		jso.put("id", element.getClientId());
		jso.put("div", div);
		
		jso.put("animation", animation);
		jso.put("animationspeed", animationspeed);
		jso.put("closeonbackgroundclick", closeonbackgroundclick);
		jso.put("dismissmodalclass", dismissmodalclass);
				
		javaScriptSupport.addInitializerCall("reveal", jso);
		
	}
	
	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}
	
}
