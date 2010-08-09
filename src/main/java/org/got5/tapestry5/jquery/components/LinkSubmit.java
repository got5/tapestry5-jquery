package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Heartbeat;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * Generates a client-side hyperlink that submits the enclosing form. If the link is clicked in the browser, the
 * component will trigger an event ({@linkplain EventConstants#SELECTED selected} by default) , just like {@link
 * Submit}.
 */
@SupportsInformalParameters
@Import(library = "linksubmit.js")
@Events(EventConstants.SELECTED + " by default, may be overridden")
public class LinkSubmit implements ClientElement
{
    /**
     * If true, then no link (or accompanying JavaScript) is written (though the body still is).
     */
    @Parameter
    private boolean disabled;

    /**
     * The name of the event that will be triggered if this component is the cause of the form submission. The default
     * is "selected".
     */
    @Parameter(allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String event = EventConstants.SELECTED;

    /**
     * If true (the default), then any notification sent by the component will be deferred until the end of the form
     * submission (this is usually desirable).
     */
    @Parameter
    private boolean defer = true;

    @Inject
    private ComponentResources resources;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;    

    @Environmental
    private FormSupport formSupport;

    @Environmental
    private Heartbeat heartbeat;

    @Inject
    private Request request;

    private String clientId;

    private static class ProcessSubmission implements ComponentAction<LinkSubmit>
    {
		private static final long serialVersionUID = 7957273412914260741L;
		private final String clientId;

        public ProcessSubmission(String clientId)
        {
            this.clientId = clientId;
        }

        public void execute(LinkSubmit component)
        {
            component.processSubmission(clientId);
        }
    }

    private void processSubmission(String clientId)
    {
        this.clientId = clientId;

        String hiddenFieldName = this.clientId + ":hidden";

        if (request.getParameter(hiddenFieldName) != null)
        {
            Runnable notification = new Runnable()
            {
                public void run()
                {
                    resources.triggerEvent(event, null, null);
                }
            };

            if (defer)
                formSupport.defer(notification);
            else
                heartbeat.defer(notification);
        }
    }

    void beginRender(MarkupWriter writer)
    {
        if (!disabled)
        {
            clientId = javaScriptSupport.allocateClientId(resources);

            formSupport.store(this, new ProcessSubmission(clientId));

            writer.element("a",

                           "id", clientId,

                           "href", "#");

            if (!request.isXHR())
                writer.attributes(MarkupConstants.ONCLICK, MarkupConstants.WAIT_FOR_PAGE);

            resources.renderInformalParameters(writer);
        }
    }

    void afterRender(MarkupWriter writer)
    {
        if (!disabled)
        {
            writer.end();
            
            JSONObject params = new JSONObject();
            params.put("formId", formSupport.getClientId());
            params.put("clientId", clientId);
            
            javaScriptSupport.addInitializerCall("linkSubmit", params);
        }
    }

    public String getClientId()
    {
        return clientId;
    }
}

