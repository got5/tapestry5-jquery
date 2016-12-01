package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.alerts.Alert;
import org.apache.tapestry5.alerts.AlertStorage;
import org.apache.tapestry5.annotations.HeartbeatDeferred;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.base.BaseClientElement;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.compatibility.DeprecationWarning;

/**
 * The JGrowl component has the same behavior as the Errors component. It will
 * use the jGrowl jQuery plugin
 * @tapestrydoc
 */
@Import(stylesheet = { "${jquery.assets.root}/vendor/components/jgrowl/jquery.jgrowl.css" })
public class JGrowl extends BaseClientElement{

	/**
	 * The JSON-type parameter for the jQuery plugin. This component will extend
	 * the default options of the jGrowl plugin by this parameter
	 */
	@Parameter
	private JSONObject params;

	 /**
     * Allows the button used to dismiss all alerts to be customized (and localized).
     *
     * @deprecated Deprecated in Tapestry 5.4; override the {@code core-dismiss-label} message key in
     *             your application's message catalog. This parameter is now ignored.
     */
    @Deprecated
    @Parameter(value = "message:core-dismiss-label", defaultPrefix = BindingConstants.LITERAL)
    private String dismissText;

    @SessionState(create = false)
    private AlertStorage storage;

    @Inject
    private DeprecationWarning deprecationWarning;

    void onPageLoaded()
    {
        deprecationWarning.ignoredComponentParameters(resources, "dismissText");
    }

    boolean beginRender(MarkupWriter writer)
    {

    	Link dismissLink = resources.createEventLink("dismiss");

        storeElement(writer.element("div",
                "data-container-type", "alerts",
                "data-dismiss-url", dismissLink));

        resources.renderInformalParameters(writer);
        writer.end();

    	addAlertsFromStorage();

        return false;
    }

    Object onDismiss(@RequestParameter(value = "id", allowBlank = true) Long alertId)
    {
        // If the alert was created inside an Ajax request and AlertStorage did not previously
        // exist, it can be null when the dismiss event comes up from the client.
        if (storage != null)
        {
            if (alertId != null)
            {
                storage.dismiss(alertId);
            } else
            {
                storage.dismissAll();
            }
        }

        return new JSONObject();
    }

    @HeartbeatDeferred
    void addAlertsFromStorage()
    {
        if (storage == null)
        {
            return;
        }

        for (Alert alert : storage.getAlerts())
        {
            javaScriptSupport.require("tjq/jgrowl").with(alert.toJSON());
        }

        storage.dismissNonPersistent();
    }
}
