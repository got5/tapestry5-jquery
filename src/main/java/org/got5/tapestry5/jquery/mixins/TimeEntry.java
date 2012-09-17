package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.json.JSONArray;

@ImportJQueryUI({ "jquery.ui.widget", "jquery.ui.core", "jquery.ui.button" })
@Import(library = {"${assets.path}/mixins/timeentry/jquery.timeentry.min.js","${assets.path}/mixins/timeentry/timeentry.js"},
	stylesheet={"${assets.path}/mixins/timeentry/jquery.timeentry.css"})
public class TimeEntry {
    /**
     * The field component to which this mixin is attached.
     */
    @InjectContainer
    private ClientElement element;

    @Environmental
    private JavaScriptSupport javaScriptSupport;
    
	@Parameter(value="../assets/mixins/timeentry/spinnerBlue.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerBlue;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerBlueBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerBlueBig;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerDefault.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerDefault;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerDefaultBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerDefaultBig;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerGem.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerGem;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerGemBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerGemBig;

	@Parameter(value="../assets/mixins/timeentry/spinnerGreen.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerGreen;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerGreenBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerGreenBig;

	@Parameter(value="../assets/mixins/timeentry/spinnerOrange.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerOrange;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerOrangeBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerOrangeBig;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerSquare.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerSquare;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerSquareBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerSquareBig;

	@Parameter(value="../assets/mixins/timeentry/spinnerText.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerText;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerTextBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerTextBig;

	@Parameter(value="../assets/mixins/timeentry/spinnerUpDown.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerUpDown;
	
	@Parameter(value="../assets/mixins/timeentry/spinnerUpDownBig.png", defaultPrefix=BindingConstants.ASSET)
	private Asset timeEntrySpinnerUpDownBig;
	/**
	 * 
	 * The Button parameters you want to override.
	 */
	@Parameter
    private JSONObject params;

    /**
     * Mixin afterRender phrase occurs after the component itself. This is where we write the
     * &lt;div&gt; element and
     * the JavaScript.
     */
	void afterRender() {
        String id = element.getClientId();

        JSONObject data = new JSONObject("id", id);
        
        if (params == null)
            params = new JSONObject();
        
        params.put("spinnerImage", timeEntrySpinnerDefault.toClientURL());
        data.put("params", params);

        javaScriptSupport.addInitializerCall("tentry", data);
	}
}