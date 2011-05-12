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
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.utils.JQueryAccordionData;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

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

	@Property
	@Parameter(required=true)
	private ArrayList<JQueryAccordionData> listOfElements;

	@Parameter(required=true)
	private int activeElementId;

	@Parameter
    private JSONObject params;

	@Property
	private JQueryAccordionData currentElement;


	@SetupRender
    void setJSInit()
    {
        setDefaultMethod("accordion");
    }

    @BeginRender
    void startDiv(MarkupWriter writer)
    {


    }

    @AfterRender
    void declareDialog(MarkupWriter writer)
    {

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
