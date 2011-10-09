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

package org.got5.tapestry5.jquery.components;


import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * The draggable component 
 * 
 * @see <a href="http://docs.jquery.com/UI/Draggable">http://docs.jquery.com/UI/Draggable</a>
 */
@ImportJQueryUI(value = {"jquery.ui.widget", "jquery.ui.mouse", "jquery.ui.draggable"})
@Import(library = { "${assets.path}/components/draggable/draggable.js" })
//@SupportsInformalParameters
public class Draggable extends AbstractExtendableComponent {


    @Environmental
    private JavaScriptSupport javaScriptSupport;

    /**
     * If provided, this is the context for the target page 
     */
    @Parameter
    private String draggablecontext;
    
	/**
	 * The Draggable parameters you want to override.
	 */
	@Parameter
    private JSONObject params;
	

	@Inject
	private ComponentResources componentResources;
	
	
	@SetupRender
	public void init(MarkupWriter w){
		w.element("div","id",getClientId());
		componentResources.renderInformalParameters(w);
		setDefaultMethod("draggable");
	}
	
    @AfterRender
	void afterRender(MarkupWriter w)
    {
    	w.end();
        String id = getClientId();

        JSONObject data = new JSONObject("id", id,"context",draggablecontext);
        JSONObject defaults = new JSONObject().put("revert", Boolean.TRUE);
        defaults.put("cusor","move");//change the cursor

        
        if (params == null)
            params = new JSONObject();

        JQueryUtils.merge(defaults, params);
        data.put("params", defaults);
       
        javaScriptSupport.addInitializerCall("draggable", data);
    }

	
}
