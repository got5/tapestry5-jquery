package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @see http://jqueryui.com/demos/dialog/
 */
public class DialogAjaxLink extends DialogLink
{

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String zone;

    @Parameter
    private Object[] context;

    @Inject
    private ComponentResources resources;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;    

    @Inject
    private AssetSource source;

    private static final String[] scripts =
    { "org/got5/tapestry5/jquery/assets/components/dialogajaxlink/dialogajaxlink.js" };

    @Override
    @SetupRender
    void setJSInit()
    {
        setDefaultMethod("dialogAjaxLink");
    }

    @Override
    @AfterRender
    void initJS(MarkupWriter writer)
    {
        writer.end();
        
        Link link = resources.createEventLink(EventConstants.ACTION, context);
        
        JSONObject params = new JSONObject();
        params.put("element", getClientId());
        params.put("zoneId", zone);
        params.put("dialogId", getDialog());
        params.put("url", link.toAbsoluteURI());

        javaScriptSupport.addInitializerCall(getInitMethod(), params);
    }

    @Override
    @AfterRender
    protected void addJSResources()
    {
        for (String path : scripts)
        {
        	javaScriptSupport.importJavaScriptLibrary(source.getClasspathAsset(path));
        }
    }

}
