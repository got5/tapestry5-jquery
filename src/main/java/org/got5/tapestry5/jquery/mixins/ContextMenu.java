package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.data.ContextMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Context Menu component.
 * @tapestrydoc
 */
@Import(stylesheet = "${jquery.assets.root}/vendor/mixins/contextmenu/jquery.contextMenu.css")
public class ContextMenu {

	private final Logger log = LoggerFactory.getLogger(getClass());

	// Component parameters

	/**
	 * Items to be listed in contextMenu.
	 * No Default Value - specification is Mandatory.
	 */
	@Parameter(required=true)
	@Property
	private List<ContextMenuItem> items;

	/**
	 * Specifies the event to show the contextMenu.
	 * Possible values: "right", "left", "hover", "none".
	 *
	 * default: right
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL, value="right")
	@Property
	private String trigger;

	/**
	 * Specifies the time in milliseconds to wait before showing the menu.
	 * Only applies to trigger="hover".
	 *
	 * default: 200
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL, value="200")
	@Property
	private int delay;

	/**
	 * Specifies if the menu must be hidden when the mouse pointer is moved out of the trigger and menu elements.
	 *
	 * default: false
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL, value="false")
	@Property
	private boolean autoHide;

	/**
	 * Specifies the offset to add to the calculated zIndex of the trigger element.
	 * Set to 0 to prevent zIndex manipulation.
	 *
	 * default: 1
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL, value="1")
	@Property
	private int zIndex;

	/**
     * The context for each link of the menu. This list of values will be converted into strings and included in
     * the URI. The strings will be coerced back to whatever their values are and made available to event handler
     * methods.
     */
    @Parameter
    private Object[] context;

	/**
	 * The zone to update when a context menu item is chosen.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String zone;

	// Injected services and components.

	@Inject
    private JavaScriptSupport javaScriptSupport;

	@Inject
    private ComponentResources resources;

    @InjectContainer
    private ClientElement element;

    // Render methods

    @AfterRender
    public void afterRender()
    {
    	JSONObject config = new JSONObject();

    	String clientId = element.getClientId();

    	config.put("id", clientId);
    	config.put("trigger", trigger);
    	config.put("delay", delay);
    	config.put("autoHide", autoHide);
    	config.put("zIndex", zIndex);

    	JSONObject jsonItems = new JSONObject();
    	int sepKeySuffix = 1;
    	JSONArray keys = new JSONArray();
    	Link link;

    	for (ContextMenuItem item : items ) {
    		if (item.getIsSeparator()) {
    			//Separator.
    			keys.put("sep" + sepKeySuffix);
    			jsonItems.put("sep" + sepKeySuffix, "---------");
    			sepKeySuffix++;
    		}
    		else {
    			//Menu entry.
	    		link = resources.createEventLink(item.event, context);

	    		JSONObject jsonItem = new JSONObject();
	    		jsonItem.put("name", item.label);
	    		jsonItem.put("icon", item.icon);
	    		jsonItem.put("url", link.toAbsoluteURI());

	    		keys.put(item.event);
	    		jsonItems.put(item.event, jsonItem);
    		}
    	}
    	config.put("items", jsonItems);
    	config.put("keys", keys);
    	if(resources.isBound("zone")){
    		log.debug("Zone is bound to {}", zone);
        	config.put("zone", zone);
        }

        javaScriptSupport.require("tjq/contextmenu").with(config);
    }
}
