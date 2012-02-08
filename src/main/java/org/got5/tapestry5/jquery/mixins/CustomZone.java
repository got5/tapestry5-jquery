package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.WidgetParams;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * This mixin allows you to override the default configuration parameters of the Effect method,
 * used by the Tapestry Zone component.
 *
 * @since 2.6.0
 * @see <a href="http://jqueryui.com/docs/effect/</a>
 */

public class CustomZone {

	/**
	 * The Effect method parameters you want to override.
	 */
	@Parameter
	private JSONObject params;

	/**
	 * The jQuery selector for calling the tapestryZone widget
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String selector;

	@Property
	private JSONObject defaultParamsObject;

	@InjectContainer
    private ClientElement element;

	@Environmental
    private JavaScriptSupport javaScriptSupport;

	@Inject
	private WidgetParams widgetParams;

	@Inject
	@Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
	private String jQueryAlias;

	@Inject
	private ComponentResources componentResources;

	@Inject
	@Symbol(SymbolConstants.COMPACT_JSON)
	private boolean compact;

	 /**
     * Mixin afterRender phrase occurs after the component itself.
     * We will :
     * 		- set the jQuery Selector
     *      - the JSON parameter
     *      - call the widget in order to change the parameter
     */
	void afterRender()
    {
    	/*
    	 * If the Selector parameter is not bound, we use the clientId of element using the mixin
    	 */
    	String theSelector = componentResources.isBound("selector") ? selector : "#"+element.getClientId();

    	/*
    	 * We will call the WidgetParams in order to get the default JSON object for the CustomZone mixin
    	 */
		defaultParamsObject = widgetParams.paramsForWidget(this.getClass().getSimpleName().toLowerCase());

		/*
		 * We will merge the default JSON Object with the params parameter
		 */
		if(defaultParamsObject!=null) JQueryUtils.merge(defaultParamsObject,params);
		else defaultParamsObject = params;

		/*
		 * We call the tapestryZone widget, in order to override the options
		 */
        javaScriptSupport.addScript(InitializationPriority.LATE,"%s('%s').tapestryZone('option',{opt: %s});", jQueryAlias, theSelector, defaultParamsObject.toString(compact));
    }
}
