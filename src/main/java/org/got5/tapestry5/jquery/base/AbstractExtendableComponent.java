package org.got5.tapestry5.jquery.base;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class AbstractExtendableComponent implements ClientElement
{
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String initMethod;

    private String defaultInitMethod;

    @Inject
    private JavaScriptSupport support;

    @Inject
    private ComponentResources resources;

    @SetupRender
    protected final void setDefaultMethod(String methodName)
    {
        this.defaultInitMethod = methodName;
    }

    @AfterRender
    protected void addJSResources()
    {
    }

    @AfterRender
    protected void addCSSResources()
    {
    }

    public void setInitMethod(String initMethod)
    {
        this.initMethod = initMethod;
    }

    public String getInitMethod()
    {
        return (initMethod == null) ? defaultInitMethod : initMethod;
    }

    public String getClientId()
    {
        if (clientId == null)
        {
            clientId = support.allocateClientId(resources);
        }

        return this.clientId;
    }
}
