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

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

@ImportJQueryUI(value = {"jquery.ui.widget", "jquery.ui.mouse", "jquery.ui.slider"})
@Import( library={ "range-slider.js" })
public class RangeSlider  {

    @Parameter(required = true)
    private Object min;
    
    @Parameter(required = true)
    private Object max;
    
    @Parameter
    private JSONObject params;
    
    @Parameter(defaultPrefix="literal")
    private String zone;

    @Parameter(value="false")
    private boolean displayTextField;
    
    private JSONObject specs;
    
    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport jsSupport;
    
    @SetupRender
    void setupRender()
    {
    	specs = new JSONObject();
    	
    	if (!resources.isBound("params"))
    		params=new JSONObject();
    	params.put("range", true);
    	specs.put("params", params);
    	specs.put("id", resources.getId());
    	
    	if(resources.isBound("zone")){
    		Link link = resources.createEventLink(EventConstants.ACTION);
    		specs.put("url", link.toAbsoluteURI());
    		specs.put("zoneId", zone);
    	}
    	
        jsSupport.addInitializerCall("rangeSlider", specs);
    }

    public String getSliderId(){
    	return resources.getId()+"-slider";
    }
    public String getMinId(){
    	return resources.getId()+"-min-field";
    }
    public String getMaxId(){
    	return resources.getId()+"-max-field";
    }
    public String getDisplayTextField(){
    	return displayTextField ? "" : "display:none;";
    }
}
