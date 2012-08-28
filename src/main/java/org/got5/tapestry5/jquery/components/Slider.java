//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

/**
 * This component allows you create a slider in a form. A range slider is a slider that is mapped to a property value. 
 * This components actually creates a Tapestry textfield component and enhanced its behaviour by adding a slider. 
 * This field is are hidden by default.
 * 
 * @since 2.1.1
 * @see <a href="http://jqueryui.com/demos/slider/">http://jqueryui.com/demos/slider/</a>
 * 
 * @tapestrydoc
 */
@SupportsInformalParameters
@ImportJQueryUI(value = {"jquery.ui.widget", "jquery.ui.mouse", "jquery.ui.slider"})
@Import( library={ "${assets.path}/components/slider/slider.js" })
public class Slider  {

    /**
     * The value associated with this slider. This is used to determine which slider will be selected when
     * the page is rendered, and also becomes the value assigned when the form is submitted.
     */
    @Parameter(required = true, principal = true)
    private Object value;

    @Parameter
    private Object[] context;

    /**
     * The slider parameters (please refer to jquery-ui documentation)
     */
    @Parameter
    private JSONObject params;
    
    /**
     * The zone to update when changes occure on the slider. An "action" event is triggered on the server. 
     * You can catch it on your page with @OnEvent(value=EventConstants.ACTION, component="sliderZone").
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String zone;

    private JSONObject specs;
    
    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport jsSupport;
    
    /**
     * A boolean indicating whether to display the textfield on the client (default is false).
     */
    @Parameter(value="false")
    private boolean displayTextField;
    
    @Component
    private TextField textfieldSlider;
    
    private String clientId;
    private String getClientId(){
    	if(clientId==null)
	    this.clientId = jsSupport.allocateClientId(this.resources);
    	return clientId;
    }
    
    @SetupRender
    void startDiv(MarkupWriter writer)
    {
        writer.element("div", "id", getSliderId());
    }
    @AfterRender
    void afterRender(MarkupWriter writer)
    {
    	resources.renderInformalParameters(writer);
    	writer.end();
    	specs = new JSONObject();
    	
    	if (!resources.isBound("params"))
    		params=new JSONObject();
    	specs.put("params", params);
    	//specs.put("textFieldId", getTextFieldId());
    	specs.put("textFieldId", textfieldSlider.getClientId());
    	specs.put("sliderId", getSliderId());
    	specs.put("displayTextField", displayTextField);
    	if(resources.isBound("zone")){
    		Link link = resources.createEventLink(EventConstants.ACTION, context);
    		specs.put("url", link.toAbsoluteURI());
    		specs.put("zoneId", zone);
    	}
        jsSupport.addInitializerCall("slider", specs);
    }

    public String getSliderId(){
    	return getClientId()+"-slider";
    }
  
}
