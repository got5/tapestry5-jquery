package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;

public class DialogLink extends AbstractExtendableComponent
{
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String dialog;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource source;

    private static final String[] scripts =
    { "org/got5/tapestry5/jquery/components/dialoglink.js" };

    @SetupRender
    void setJSInit()
    {
        setDefaultMethod("dialogLink");
    }

    @BeginRender
    void startLink(MarkupWriter writer)
    {
        writer.element("a", "href", "#");
        writer.getElement().forceAttributes("id", getClientId());
    }

    @AfterRender
    void initJS(MarkupWriter writer)
    {
        writer.end();
        
        JSONObject params = new JSONObject();
        params.put("triggerId", getClientId());
        params.put("dialogId", dialog);
        
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

    protected String getDialog()
    {
        return this.dialog;
    }

}
