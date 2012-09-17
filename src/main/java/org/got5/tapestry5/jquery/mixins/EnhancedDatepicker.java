package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

@ImportJQueryUI({ "jquery.ui.widget", "jquery.ui.core", "jquery.ui.button" })
@Import(library = {"${assets.path}/mixins/enhanceddatepicker/jquery.datepick.min.js","${assets.path}/mixins/enhanceddatepicker/datepick.js"},
	stylesheet={"${assets.path}/mixins/enhanceddatepicker/jquery.datepick.css"})
public class EnhancedDatepicker {
	@Parameter
	@Property
	private JSONObject params;

	@Parameter(value="../assets/mixins/enhanceddatepicker/calendar.gif", defaultPrefix=BindingConstants.ASSET)
	private Asset calDefault;
	
	@Parameter(value="../assets/mixins/enhanceddatepicker/calendar-blue.gif", defaultPrefix=BindingConstants.ASSET)
	private Asset calBlue;
	
	@Parameter(value="../assets/mixins/enhanceddatepicker/calendar-green.gif", defaultPrefix=BindingConstants.ASSET)
	private Asset calGreen;

	@Property
	private String id;

	@InjectContainer
    private ClientElement element;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private ComponentResources resources;

	@BeginRender
	public void beginRender() {
		id = javaScriptSupport.allocateClientId(resources);
	}

	@AfterRender
	public void afterRender() {
		JSONObject data = new JSONObject();
		id = element.getClientId();
		data.put("id", id);

		if (params == null) {
			params = new JSONObject();
		}
		
		params.put("showTrigger", "<img src='" + calBlue.toClientURL() + "' alt='Popup' class='trigger'>");
		params.put("showAnim", "fadeIn");
		data.put("params", params);
		javaScriptSupport.addInitializerCall("enhdatepick", data);
	}
}
