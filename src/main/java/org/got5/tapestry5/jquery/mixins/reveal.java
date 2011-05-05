package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"${assets.path}/mixins/reveal/jquery.reveal.js","${assets.path}/mixins/reveal/reveal.js"},stylesheet={"${assets.path}/mixins/reveal/reveal.css"})

public class reveal{
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String div;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL,
				value="fadeAndPop")
	private String animation;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL,
			value="300")
	private Integer animationspeed;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL,
				value="true")
	private boolean closeonbackgroundclick;
	
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
