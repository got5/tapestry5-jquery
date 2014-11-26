package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.alerts.Alert;
import org.apache.tapestry5.alerts.AlertStorage;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


/**
 * The JGrowl component has the same behavior as the Errors component. It will
 * use the jGrowl jQuery plugin
 *
 * @tapestrydoc
 */
@Import(library = {"${assets.path}/components/jgrowl/jquery.jgrowl.js", "${assets.path}/components/jgrowl/jGrowl_init.js"},
				stylesheet={"${assets.path}/components/jgrowl/jquery.jgrowl.css"})
public class JGrowl {

		/**
		 *  The JSON-type parameter for the jQuery plugin. This component
		 *  will extend the default options of the jGrowl plugin by this parameter
		 */
		@Parameter
		private JSONObject params;

	    @Inject
	    private ComponentResources resources;

	    @Environmental
	    private JavaScriptSupport javaScriptSupport;

	    @SessionState(create = false)
	    private AlertStorage storage;

	    @Inject
	    private Request request;

	    boolean beginRender(MarkupWriter writer)
	    {
	        JSONObject spec = new JSONObject("dismissURL", resources.createEventLink("dismiss").toURI());

	        spec.put("jgrowl", params);

	        javaScriptSupport.addInitializerCall(InitializationPriority.EARLY, "jGrowlAlertManager", spec);

	        if (storage != null)
	        {
	        	JSONObject json = new JSONObject();

	        	for (Alert alert : storage.getAlerts())
	            {
	        		javaScriptSupport.addInitializerCall("addjGrowlAlert", alert.toJSON());
	            }

	            storage.dismissNonPersistent();
	        }


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

	        // #336 you cannot return JSONObject in non XHR requests
	        if (request.isXHR()) {

	            return new JSONObject();
	        }

	        return null;
	    }
}
