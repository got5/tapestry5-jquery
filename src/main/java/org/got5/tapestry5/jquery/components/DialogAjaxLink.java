package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

public class DialogAjaxLink extends DialogLink
{

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String zone;

    @Parameter
    private Object[] context;

    @Inject
    private ComponentResources resources;

    @Inject
    private RenderSupport support;

    @Inject
    private AssetSource source;

    private static final String[] scripts =
    { "org/got5/tapestry5/jquery/components/dialogajaxlink.js" };

    @SetupRender
    void setJSInit()
    {
        setDefaultMethod("dialogAjaxLink");
    }

    @AfterRender
    void initJS(MarkupWriter writer)
    {
        writer.end();
        
        Link link = resources.createEventLink(EventConstants.ACTION, context);

        support.addInit(getInitMethod(), getClientId(), zone, getDialog(), link.toAbsoluteURI());
    }

    @AfterRender
    protected void addJSResources()
    {
        for (String path : scripts)
        {
            support.addScriptLink(source.getClasspathAsset(path));
        }
    }

}
