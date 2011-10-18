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

import java.util.ArrayList;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.utils.JQueryAccordionData;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * @see <a href="http://jqueryui.com/demos/accordion/">http://jqueryui.com/demos/accordion/</a>
 */
@SupportsInformalParameters
@ImportJQueryUI( value = { "jquery.ui.core",
                           "jquery.ui.widget",
                           "jquery.ui.accordion" })
@Import(library =          "${assets.path}/components/accordion/accordion.js")
public class Accordion extends AbstractExtendableComponent
{
	@Inject
	private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

	/**
	 *  A list of JQueryAccordionData (object containing the title of the tab and the name of the block that has the content).
	 */
	@Property
	@Parameter(required=true)
	private ArrayList<JQueryAccordionData> listOfElements;

	/**
	 * The number of the accordion tab to activate when the page is displayed on the client.
	 */
	@Parameter(required=true)
	private int activeElementId;

	/**
	 * The slider parameters (please refer to jquery-ui documentation)
	 */
	@Parameter
    private JSONObject params;

	@Property
	private JQueryAccordionData currentElement;

	void setupRender()
    {
        setDefaultMethod("accordion");
    }
	
	@AfterRender
    void afterRender(MarkupWriter writer)
    {
    	resources.renderInformalParameters(writer);
        JSONObject data = new JSONObject();
        data.put("id", getClientId());

        if (params == null)
            params = new JSONObject();

        JSONObject defaults = new JSONObject();
        defaults.put("active", activeElementId);

        JQueryUtils.merge(defaults, params);
        data.put("params", defaults);


        javaScriptSupport.addInitializerCall(getInitMethod(), data);

    }

	public Block getCurrentBlock()
	{
		String blockName=currentElement.getBlockName();
		return resources.getContainer().getComponentResources().getBlock(blockName);
	}

}
