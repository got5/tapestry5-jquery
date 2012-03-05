package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;

/**
 * There are a few components you can use to create a dialog in your pages. 
 *   - Dialog, this is the base of your Dialog, just put the content of your dialog inside.
 * 	- DialogLink, clicking a DialogLink will make the associated Dialog to open.
 *	- DialogAjaxLink, clicking a DialogAjaxLink will open the associated Dialog and refresh the zone you've set inside the Dalog.
 *
 * @see <a href="http://jqueryui.com/demos/dialog/">http://jqueryui.com/demos/dialog/</a>
 * 
 * @tapestrydoc
 */
@SupportsInformalParameters
public class DialogLink extends AbstractExtendableComponent
{
    /**
     * The id of the dialog to open.
     */
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String dialog;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource source;

    @Inject
    private ComponentResources resources;
    
    private static final String[] scripts =
    { "org/got5/tapestry5/jquery/assets/components/dialoglink/dialoglink.js" };

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
    	resources.renderInformalParameters(writer);
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
