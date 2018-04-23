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

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.utils.JQueryTabData;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * This component allows you create a jquery ui tab.
 *
 * @see <a href="http://jqueryui.com/tabs/">jQuery UI Official Documentation</a>
 * @tapestrydoc
 */
@SupportsInformalParameters
public class Tabs extends AbstractExtendableComponent
{
	/**
	 *  A list of JQueryTabData (object containing the title of the tab and the name of the block that has the content).
	 */
	@Property
	@Parameter
	private ArrayList<JQueryTabData> listTabData;

	/**
	 * The number of the tab to activate when the page is displayed on the client.
	 */
	@Parameter(required=true)
	@Property
	private int activePanelId;

	/**
	 *  A comma-separated list of strings, corresponding to yours blocks
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String tabs;

	/**
	 * Indicate if you want to load your block by ajax.
	 */
	@Property
	@Parameter(value="true", defaultPrefix=BindingConstants.LITERAL)
	private Boolean ajax;

	/**
	 * The slider parameters (please refer to jquery-ui documentation)
	 */
	@Parameter
    private JSONObject params;

	/**
     * Defines where block and label overrides are obtained from.
     */
    @Parameter(value = "this", allowNull = false)
    @Property(write = false)
    private PropertyOverrides overrides;

	@Inject
	private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource source;

	private String clientZoneId;

	@Property
	private JQueryTabData currentTabData;

	@Property
	private int currentPanelId;

	@Property
	private String tab;


	@SetupRender
    void setJSInit(MarkupWriter writer)
    {
        setDefaultMethod("tabs");
        writer.element("div", "id", getClientId());
    }


    @AfterRender
    void declareTabs(MarkupWriter writer)
    {
    	resources.renderInformalParameters(writer);
    	writer.end();
        JSONObject data = new JSONObject();
        data.put("id", getClientId());

        if (params == null)
            params = new JSONObject();

        JSONObject defaults = new JSONObject();
        defaults.put("cache", false);
        defaults.put("active", activePanelId);

        JQueryUtils.merge(defaults, params);

        data.put("params", defaults);

        javaScriptSupport.require("tjq/ui").invoke("tabs").with(data);
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
        if(getOlderVersion())
        	return new Object[] { currentTabData.getBlockName(), currentPanelId };

        return new Object[] { tab, currentPanelId };
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

		if(getOlderVersion())
			return resources.getContainer().getComponentResources().getBlock(blockName);

		resources.triggerEvent(JQueryEventConstants.SELECT_TAB, new Object[] { activePanelId }, null);

		return overrides.getOverrideBlock(blockName);
	}


	public Block getActiveBlock()
	{
		if(getOlderVersion()){

			String blockName = ajax ? listTabData.get(activePanelId).getBlockName() : currentTabData.getBlockName();

			return resources.getContainer().getComponentResources().getBlock(blockName);
		}

		if(ajax)
			return overrides.getOverrideBlock(getTabs()[activePanelId]);
		return overrides.getOverrideBlock(tab);
	}

	public boolean getOlderVersion(){
		return resources.isBound("listTabData");
	}

	public String[] getTabs()
	{
		return TapestryInternalUtils.splitAtCommas(tabs);
	}

	/**
	 * First, Tapestry5-jQuery will look for the label in an associated
	 * bundle, with the name of the tab as a key. If the message does not exist
	 * Tapestry5-jQuery will provide a default value :  the name of the tab, with
	 * capital letters and space.
	 *
	 * @return the label of a tab
	 */
	public String getTabTitle(){

		if(overrides.getOverrideMessages().contains(tab))
		{
			return overrides.getOverrideMessages().get(tab);
		}

		return TapestryInternalUtils.toUserPresentable(tab);
	}

}
