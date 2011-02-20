//
// Copyright 2010 GOT5 (GO Tapestry 5)
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

package org.got5.tapestry5.jquery.mixins;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;
import org.got5.tapestry5.jquery.utils.JQueryUtils;


@Import(library = 
{ "${tapestry.jquery.path}/ui_1_8/jquery.ui.widget.js", 
  "${tapestry.jquery.path}/ui_1_8/jquery.ui.core.js",
  "${tapestry.jquery.path}/ui_1_8/jquery.ui.button.js", 
  "${tapestry.jquery.path}/mixins/button.js" })
public class Button
{
   
    /**
     * The field component to which this mixin is attached.
     */
    @InjectContainer
    private ClientElement element;

    @Inject
    private ComponentResources resources;
    
    @Environmental
    private JavaScriptSupport javaScriptSupport;    

 
    static final String TYPE_BUTTON = "button";
	static final String TYPE_BUTTONSET = "buttonset";

	
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = TYPE_BUTTON)
	private String type;

	@Parameter
    private JSONObject params;

    /**
     * Mixin afterRender phrase occurs after the component itself. This is where we write the
     * &lt;div&gt; element and
     * the JavaScript.
     * 
     * @param writer
     */
    void afterRender(MarkupWriter writer)
    {
        String id = element.getClientId();

        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("type", type);


        JSONObject defaults = new JSONObject();
        defaults.put("type", type);
        
        if (params == null)
            params = new JSONObject();

        JQueryUtils.merge(defaults, params);
        data.put("params", defaults);
        

        javaScriptSupport.addInitializerCall("button", data);
    }

   
    
}
