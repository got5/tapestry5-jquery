package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.HeartbeatDeferred;
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
@Import(stylesheet={"${jquery.assets.root}/vendor/mixins/reveal/reveal.css"})
public class Reveal{

	/**
	 * The id of the div you want to display.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String div;

	@Parameter(name = "for", allowNull = false, defaultPrefix = BindingConstants.COMPONENT)
    private ClientElement revealElement;

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

	/**
	 * The jQuery Selector for the element used to display the modal window (a link most of the time)
	 * By default, the #id of the element.
	 *
	 * @since 3.2.0
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL, value="prop:clientId")
	private String selector;

	@InjectContainer
	private ClientElement element;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	private JSONObject parameters;

	@AfterRender
	public void finish(MarkupWriter w){

	    parameters = new JSONObject();
	    parameters.put("id", selector);

		if (revealElement == null) {

		    parameters.put("div", div);

		} else {

		    updateParameters();
		}

		parameters.put("animation", animation);
		parameters.put("animationspeed", animationspeed);
		parameters.put("closeonbackgroundclick", closeonbackgroundclick);
		parameters.put("dismissmodalclass", dismissmodalclass);

		javaScriptSupport.require("tjq/reveal").with(parameters);

	}

    @HeartbeatDeferred
    private void updateParameters() {

        parameters.put("div", revealElement.getClientId());
    }

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getClientId(){
		return "#"+element.getClientId();
	}

}
