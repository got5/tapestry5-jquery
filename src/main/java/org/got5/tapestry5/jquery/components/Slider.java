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
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.base.AbstractSlider;

@Import( library={ "slider.js" })
public class Slider extends AbstractSlider {

    /**
     * The value associated with this slider. This is used to determine which slider will be selected when
     * the page is rendered, and also becomes the value assigned when the form is submitted.
     */
    @Parameter(required = true, principal = true, autoconnect = true)
    private Object value;
    
    @Parameter
    private JSONObject params;

    @Parameter
    private JSONObject specs;
    
    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport jsSupport;

    private String clientId;

    private String controlName;

    
    void beginRender(MarkupWriter writer)
    {
    	
    	writer.element("div", "id", getClientId()+"-slider");
    	
    	writer.end();
    	
    	writer.element("input",

                "type", "text",

                "name", getControlName(),

                "id", getClientId(),

                "value", value, 
                
                "style", "display:none;"
    		);
    	
    	specs = new JSONObject();
    	
    	if (!resources.isBound("params"))
    		params=new JSONObject();

    	specs.put("params", params);
    	
    	
    	specs.put("id", getClientId());
        jsSupport.addInitializerCall("slider", specs);
    }

    void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

}
