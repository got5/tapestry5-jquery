package org.got5.tapestry5.jquery.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.utils.JQueryTabData;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

@Import(library= {
		"classpath:org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.core.min.js",
		"classpath:org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.widget.min.js",
		"classpath:org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.tabs.min.js",
		"tabs.js"})
public class Tabs extends AbstractExtendableComponent
{
	@Inject
	private ComponentResources resources;
	
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource source;
	
	@Property
	@Parameter(required=true)
	private ArrayList<JQueryTabData> listTabData;
	
	@Parameter(required=true)
	private int activePanelId;	
	
	@Parameter
    private JSONObject params;
	
	@Property
	private JQueryTabData currentTabData;
	
	@Property
	private int currentPanelId;

	@SetupRender
    void setJSInit()
    {
        setDefaultMethod("tabs");
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
        defaults.put("cache", false);
        defaults.put("selected", activePanelId);

        JQueryUtils.merge(defaults, params);

        data.put("params", defaults);


        javaScriptSupport.addInitializerCall(getInitMethod(), data);
    
    }
  

	Object onSelectTab(String blockName)
	{
		Block bl = (Block)resources.getContainer().getComponentResources().getBlock(blockName);
		return bl;
	}
  
	
	public Block getActiveBlock()
	{
		String blockName=listTabData.get(activePanelId).getBlockName();
		return resources.getContainer().getComponentResources().getBlock(blockName);
	}



}
