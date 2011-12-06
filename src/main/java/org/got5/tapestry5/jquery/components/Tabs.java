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

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.utils.JQueryTabData;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * This component allows you create a jquery ui tab.
 *
 * @see <a href="http://jqueryui.com/demos/tabs/">http://jqueryui.com/demos/tabs/</a>
 */
@SupportsInformalParameters
@ImportJQueryUI(value = { "jquery.ui.core",
                          "jquery.ui.widget",
                          "jquery.ui.tabs" })
@Import(library =         "${assets.path}/components/tabs/tabs.js")
@Events(JQueryEventConstants.SELECT_TAB)
public class Tabs extends AbstractExtendableComponent
{
	@Inject
	private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource source;

	/**
	 *  A list of JQueryTabData (object containing the title of the tab and the name of the block that has the content).
	 */
	@Property
	@Parameter(required=true)
	private ArrayList<JQueryTabData> listTabData;

	private String clientZoneId;

	/**
	 * The number of the tab to activate when the page is displayed on the client.
	 */
	@Parameter(required=true)
	@Property
	private int activePanelId;

	/**
	 * The slider parameters (please refer to jquery-ui documentation)
	 */
	@Parameter
    private JSONObject params;

	@Property
	private JQueryTabData currentTabData;

	@Property
	private int currentPanelId;

    /**
     * Affects how the component's internal Zone behaves on zone updates
     * triggered by tab-changes.
     */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String update;

	@SuppressWarnings("unused")
    @Property
	private String updateMethod;

	@BeginRender
    void setJSInit()
    {
        setDefaultMethod("tabs");

        if (StringUtils.isBlank(update)) {

            updateMethod = "highlight";

        } else {

            updateMethod = update;
        }
    }

    @AfterRender
    void declareTabs(MarkupWriter writer)
    {
    	resources.renderInformalParameters(writer);
        JSONObject data = new JSONObject();
        data.put("id", getClientId());

        if (params == null)
            params = new JSONObject();

        JSONObject defaults = new JSONObject();
        defaults.put("cache", false);
        defaults.put("selected", activePanelId);

        JQueryUtils.merge(defaults, params);

        data.put("params", defaults);

        javaScriptSupport.addInitializerCall(getInitMethod(), data);
    }

    public String getClientZoneId(){
    	this.clientZoneId = getClientId()+"-zone";
    	return this.clientZoneId;
    }
    public void setClientZoneId(String id){
    	this.clientZoneId = id;
    }
    public Object[] getTabContext()
    {
        return new Object[] { currentTabData.getBlockName(), currentPanelId };
    }


	Object onSelectTab(String blockName, int panelIndex)
	{
		try
		{
			if(resources.isBound("activePanelId"))
			activePanelId =panelIndex;
		}
		catch(Exception ex)
		{
			//org.apache.tapestry5.runtime.ComponentEventException: Failure writing parameter 'activePanelId'
			// of component docs/Calendar:tabs: Literal values are not updateable
		}

		resources.triggerEvent(JQueryEventConstants.SELECT_TAB, new Object[] { activePanelId }, null);

		return resources.getContainer().getComponentResources().getBlock(blockName);
	}

	public Block getActiveBlock()
	{
		String blockName=listTabData.get(activePanelId).getBlockName();
		return resources.getContainer().getComponentResources().getBlock(blockName);
	}

}
