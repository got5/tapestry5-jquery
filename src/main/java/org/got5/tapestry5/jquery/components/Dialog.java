package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

@ImportJQueryUI(value = { "jquery.ui.mouse", "jquery.ui.draggable", "jquery.ui.resizable", "jquery.ui.dialog" })
public class Dialog implements ClientElement
{
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Parameter
    private JSONObject params;

    @Parameter("literal:dialog")
    private String initMethod;

    @Inject
    private AssetSource source;

    @Inject
    private JavaScriptSupport support;

    @BeginRender
    void startDiv(MarkupWriter writer)
    {
        writer.element("div", "id", getClientId());
    }

    @AfterRender
    void declareDialog(MarkupWriter writer)
    {
        writer.end();

        JSONObject data = new JSONObject();
        data.put("id", getClientId());

        if (params == null)
            params = new JSONObject();

        JSONObject defaults = new JSONObject();
        defaults.put("modal", false);
        defaults.put("resizable", false);
        defaults.put("draggable", false);
        defaults.put("autoOpen", false);

        JQueryUtils.merge(defaults, params);

        data.put("params", defaults);

        configure(data);

        support.addInitializerCall(initMethod, data);
    }

    @AfterRender
    protected void addJSResources()
    {
        String[] scripts = { "org/got5/tapestry5/jquery/components/dialog.js" };

        for (String path : scripts)
        {
            support.importJavaScriptLibrary(source.getClasspathAsset(path));
        }
    }

    @AfterRender
    protected void addCSSResources()
    {
    }

    protected void configure(JSONObject params)
    {
    }

    public String getClientId()
    {
        return this.clientId;
    }

}
