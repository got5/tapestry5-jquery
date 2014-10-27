package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.components.AjaxUpload;

public class JQuerySymbolConstants {

	/**
	 * The base path of the replaced version for tapestry.js. Also the
	 * resources-base for {@link AjaxUpload}.
	 */
	public static final String TAPESTRY_JQUERY_PATH = "tapestry.jquery.path";

	/**
	 * Path to the overridden tapestry.js. The file re-implements the bare API
	 * that is given by the original T5 tapestry.js.
	 */
	public static final String TAPESTRY_JS_PATH = "tapestry.js.path";

	/**
	 * Base path for jQuery. jquery-${jquery.version}.js is assumed to be in
	 * there.
	 */
	public static final String JQUERY_CORE_PATH = "jquery.core.path";

	/**
	 * The jQuery version number. Must match the normal jQuery file name
	 * pattern: <code>jquery-${jquery.version}.js</code> or
	 * <code>jquery-${jquery.version}.min.js</code>
	 */
	public static final String JQUERY_VERSION = "jquery.version";

	/**
	 * Base path for jQuery UI. Must include a "minified" directory that
	 * contains the .min-files for production mode.
	 */
	public static final String JQUERY_UI_PATH = "jquery.ui.path";

	/**
	 * Path to the theme-css file for jQuery UI.
	 */
	public static final String JQUERY_UI_DEFAULT_THEME = "jquery.ui.default-theme.path";

	/**
	 * Base path for jQuery validation plugin.
	 * 
	 * @see <a href="http://docs.jquery.com/Plugins/Validation">jQuery
	 *      Validation mecanism</a>
	 */
	public static final String JQUERY_VALIDATE_PATH = "jquery.validate.path";

	/**
	 * Indicates that we want to drop out prototype and use only jquery
	 */
	public static final String SUPPRESS_PROTOTYPE = "suppress.prototype";

	/**
	 * Indicates that we want to drop out prototype and use only jquery
	 */
	public static final String ASSETS_PATH = "assets.path";

	/**
	 * Indicates the jQuery Alias we want to use : $, jQuery, ...
	 */
	public static final String JQUERY_ALIAS = "jquery.alias";

	public static final String PROTOTYPE_STACK = "prototype.js.stack";

	public static final String PARAMETER_PREFIX = "parameter.prefix";

	/**
	 * Indicates that we want use the minified version of JavaScript files
	 */
	public static final String USE_MINIFIED_JS = "use.minified.js";

	/**
	 * 
	 * Indicates if your application should manage the mousewheel event, from
	 * the
	 * 
	 * the jQuery Mouse Wheel Plugin
	 * (http://brandonaaron.net/code/mousewheel/demos)
	 */

	public static final String ADD_MOUSEWHEEL_EVENT = "jquery.mousewheel";

	public static final String INCLUDE_DATEPICKER_I18N = "jquery.include_datepicker";
	
	/**
	 * Indicates that we want to excude the core stack, could be needed
	 * in case we are working with portlets, to not include stacks twice
	 * when 2 wars are used for example
	 */
	public static final String EXCLUDE_CORE_JS_STACK="exclude.core.stack";
}
