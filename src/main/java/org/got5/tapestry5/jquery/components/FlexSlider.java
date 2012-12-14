package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.MarkupWriterListener;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.javascript.FlexSliderStack;

@Import(stack = FlexSliderStack.STACK_ID)
public class FlexSlider {
	@Parameter
	private JSONObject params;
	
	@Property
	private String id;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
    private ComponentResources resources;
	
	@SetupRender
	public void beginRender(MarkupWriter writer) {
		id = javaScriptSupport.allocateClientId(resources);
		writer.addListener(new MarkupWriterListener() {
			
			public void elementDidStart(Element element) {
				// TODO Auto-generated method stub
				
			}
			
			public void elementDidEnd(Element element) {
				if(element.getName().equalsIgnoreCase("ul") && hasSlidesClass(element))
					element.addClassName("slides");
			}

			private boolean hasSlidesClass(Element element) {
				return InternalUtils.isBlank(element.getAttribute("class")) || 
						(InternalUtils.isNonBlank(element.getAttribute("class")) && !element.getAttribute("class").contains("slides"));
			}
		});
	}
	
	@AfterRender
	public void afterRender() {

		if(params == null) params = new JSONObject();
	    JSONObject opt = new JSONObject();
	    opt.put("id", id);
	    opt.put("params", params);
	    
		
		javaScriptSupport.addInitializerCall("flexslider", opt);
	}

	
}
