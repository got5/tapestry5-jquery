package org.got5.tapestry5.jquery.mixins;

import java.text.SimpleDateFormat;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.WidgetParams;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * This mixin allows you to override the default configuration parameters of the
 * Datepicker
 * 
 * @since 2.1.1
 * @see <a href="http://jqueryui.com/datepicker/">jQuery UI Official
 *      Documentation</a>
 *
 * @author Emmanuel DEMEY
 * @tapestrydoc
 */
public class CustomDatepicker {

	/**
	 * The DatePicker parameters you want to override.
	 */
	@Parameter
	private JSONObject params;

	/**
	 * the jQuery selector for the datepicker widget
	 */
	@Parameter
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
	private ComponentResources componentResources;

	@Inject
	@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE)
	private Boolean suppressPrototype;

	@Inject
	private ThreadLocale locale;

    @BindParameter
    private Object format;

    @Inject
    private AssetSource as;

    @Inject
    private TypeCoercer typeCoercer;



	/**
	 * Mixin afterRender phrase occurs after the component itself. We will
	 * change the JSON option of the jQuery datepicker widget
	 */
	void afterRender() {
		if (suppressPrototype) {
			/*
			 * If the selector parameter is not bound, we will use the clientId
			 * of the component using the mixin
			 */
			String theSelector = componentResources.isBound("selector") ? selector
					: "#" + element.getClientId();

			/*
			 * We will call the WidgetParams in order to get the default JSON
			 * object for the CustomDatepicker mixin
			 */
            defaultParamsObject = new JSONObject();

            JSONObject temp = widgetParams.paramsForWidget(this.getClass().getSimpleName().toLowerCase());
            if(temp != null){
                defaultParamsObject = temp.copy();
            }

            defaultParamsObject.put("dateFormat", toJQueryUIDateFormat());
			/*
			 * We will merge the default JSON Object with the params parameter
			 */
            JQueryUtils.merge(defaultParamsObject, params);

			defaultParamsObject.put("selector", theSelector);
			/*
			 * We call the datepicker widget, in order to override the options
			 */

            JSONObject json = new JSONObject();
            json.put("selector", theSelector);
            json.put("params", defaultParamsObject);

			javaScriptSupport.require("tjq/customdatepicker")
					.priority(InitializationPriority.LATE)
					.with(json);
		}
	}

	private String getLocale() {
		final String prefix = String.format("classpath:/META-INF/modules/tjq/vendor/ui/i18n/jquery.ui.datepicker-%s", locale.getLocale().getLanguage());
        final Resource withCountryExtension = typeCoercer.coerce(String.format("%s-%s.js", prefix, locale.getLocale().getCountry()), Resource.class);

        if (withCountryExtension.exists()) {
        	return String.format("%s-%s", locale.getLocale().getLanguage(), locale.getLocale().getCountry());
        }

        final Resource withLanguageExtension = typeCoercer.coerce(String.format("%s.js", prefix), Resource.class);

        if (withLanguageExtension.exists()) {

        	return locale.getLocale().getLanguage();
        }
        return "en-GB";
	}


    /**
     *
     * ISSUE #294
     */
    private String toJQueryUIDateFormat() {

        if (format == null) {
            return null;
        }
        else if (format instanceof SimpleDateFormat) {
            String pattern = ((SimpleDateFormat) format).toPattern();

            // Year
            if (pattern.contains("yyyy")) {
                pattern = pattern.replaceAll("yyyy", "yy");
            }
            else {
                pattern = pattern.replaceAll("yy", "y");
            }

            // Month
            if (pattern.contains("MMMM")) {
                pattern = pattern.replace("MMMM", "MM");
            }
            else if (pattern.contains("MMM")) {
                pattern = pattern.replace("MMM", "M");
            }
            else if (pattern.contains("MM")) {
                pattern = pattern.replace("MM", "mm");
            }
            else if (pattern.contains("M")) {
                pattern = pattern.replace("M", "m");
            }

            // Day
            if (pattern.contains("DD")) {
                pattern = pattern.replace("DD", "oo");
            }
            else if (pattern.contains("D")) {
                pattern = pattern.replace("D", "o");
            }

            // Day of month
            if (pattern.contains("EEEE")) {
                pattern = pattern.replace("EEEE", "DD");
            }
            else if (pattern.contains("EEE")) {
                pattern = pattern.replace("EEE", "D");
            }

            return pattern;
        }
        else {
            // Don't know how to extract the date pattern from this type of DateFormat.
            throw new IllegalArgumentException("Type is " + format.getClass().getName());
        }
    }
}
