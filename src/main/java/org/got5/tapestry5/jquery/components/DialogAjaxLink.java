package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * There are a few components you can use to create a dialog in your pages.
 *  - Dialog, this is the base of your Dialog, just put the content of your dialog inside.
 * 	- DialogLink, clicking a DialogLink will make the associated Dialog to open.
 *	- DialogAjaxLink, clicking a DialogAjaxLink will open the associated Dialog and refresh the zone you've set inside the Dalog.
 *
 * @see <a href="http://jqueryui.com/dialog/">jQuery UI Official Documentation</a>
 * @tapestrydoc
 */
@SupportsInformalParameters
public class DialogAjaxLink extends DialogLink
{

    /**
     * The id of the zone to refresh.
     */
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String zone;

    /**
     * The activation context.
     */
    @Parameter
    private Object[] context;

    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource source;

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
    	resources.renderInformalParameters(writer);
        writer.end();

        Link link = resources.createEventLink(EventConstants.ACTION, context);

        JSONObject params = new JSONObject();
        params.put("element", getClientId());
        params.put("zoneId", zone);
        params.put("dialogId", getDialog());
        params.put("url", link.toURI());

        javaScriptSupport.require("tjq/dialogajaxlink").with(params);
    }



}
