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
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

/**
 * This component allows you create a range slider in a form. A range slider is a slider that has a min and a max value. This components actually creates 2 Tapestry textfields components and enhanced their behaviour by adding a slider. 
 * These 2 fields are hidden by default.
 * 
 * @since 2.1.1
 * @see <a hred="http://jqueryui.com/demos/slider/">http://jqueryui.com/demos/slider/</a>
 */
@ImportJQueryUI(value = {"jquery.ui.widget", "jquery.ui.mouse", "jquery.ui.slider"})
@Import( library={ "${assets.path}/components/rangeslider/range-slider.js" })
public class RangeSlider  {

    /**
     * The value to be read and updated, the min slider value. This value is passed to the first textfield.
     */
    @Parameter(required = true)
    private Object min;
    
    /**
     * The value to be read and updated, the max slider value. This value is passed to the second textfield.
     */
    @Parameter(required = true)
    private Object max;
    
    /**
     * The slider parameters (please refer to jquery-ui documentation)
     */
    @Parameter
    private JSONObject params;
    
    /**
     * The zone to update when changes occure on the slider. 
     * An "action" event is triggered on the server. 
     * You can catch it on your page with @OnEvent(value=EventConstants.ACTION, component="sliderRangeZone").
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String zone;

    /**
     * A boolean indicating whether to display the 2 textfields on the client (default is false).
     */
    @Parameter(value="false")
    private boolean displayTextField;
    
    private JSONObject specs;
    
    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport jsSupport;
    
    @Component
    private TextField maxField, minField;
    
    private String clientId;
    private String getClientId(){
    	if(clientId==null)
    		clientId = resources.getId();
    	return clientId;
    }
    
    void afterRender()
    {
    	specs = new JSONObject();
    	
    	if (!resources.isBound("params"))
    		params=new JSONObject();
    	params.put("range", true);
    	specs.put("params", params);
    	specs.put("sliderId", getSliderId());
    	specs.put("displayTextField",displayTextField);
    	specs.put("idMinField", minField.getClientId());
    	specs.put("idMaxField", maxField.getClientId());
    	if(resources.isBound("zone")){
    		Link link = resources.createEventLink(EventConstants.ACTION);
    		specs.put("url", link.toAbsoluteURI());
    		specs.put("zoneId", zone);
    	}
    	
        jsSupport.addInitializerCall("rangeSlider", specs);
    }

    public String getSliderId(){
    	return getClientId()+"-slider";
    }

    public String getDisplayTextField(){
    	return displayTextField ? "" : "display:none;";
    }
}
