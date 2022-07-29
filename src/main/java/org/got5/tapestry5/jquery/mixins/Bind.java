package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.EffectsConstants;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @since 2.6.0
 *
 * @tapestrydoc
 */
public class Bind {

	/**
	 * Used as the selector for jQuery bind.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String element;

	/**
	 * 	Tapestry event name. For example if you supply click onClick() will be called.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String event;

	/**
	 * ID of element to hide on this event.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String hide;

	/**
	 * jQuery effect used to hide the element.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "slide")
	private String hideEffect;

	/**
	 * Duration of hide effect.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "500")
	private String hideTime;

	/**
	 * Any options need by the hide effect.
	 */
	@Parameter
	private Map<String, String> hideOptions;

	/**
	 * If supplied a zone update will be triggered.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String zone;

	/**
	 * Effect used when zone is updating.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "highlight")
	private String zoneUpdate;

	/**
	 * Javascript anonymous function called by the event. The signature is function(event,ui,url)
	 * where event and ui are described by jQuery bind.
	 * The url parameter is structure representing the Tapestry event url and contains the url and
	 * a function to add a context. See examples below.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String callback;

	/**
	 * The context used to construct the event link.
	 */
	@Parameter
	private Object[] context;

	/**
	 * When an event is triggered the page title is changed to this value.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	/**
	 * Javascript anonymous function called by the event. Used to manage history on the browser.
	 * Signature is history(event,ui,url). The parameter types are identical to callback.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String history;

	/**
	 * Name of the jQuery event. By subclassing bind you can create mixins named by the jQuery event type.
	 * This allows more than one event per element.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String eventType;

	/**
	 * If true calls event.preventDefault().
	 */
	@Parameter(value = "true")
	private Boolean preventDefault;

	/**
	 * If true bind will attempt to include the right effect libraries for zone and hide.
	 */
	@Parameter(value = "true")
	private Boolean doImports;

	/**
	 * Used as a marker by url.addContext() to insert values into the Tapestry event url.
	 */
	@Parameter(value = "CoNtExT", defaultPrefix = BindingConstants.LITERAL)
	private String contextMarker;

	@Inject
	private AssetSource assetSource;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@InjectContainer
	private ClientElement clientElement;

	@Inject
	private ComponentResources resources;

	String element() {
		if (element != null) {
			return element;
		}
		return clientElement.getClientId();
	}

	String eventType() {
		if (eventType != null) {
			return eventType;
		}
		return this.getClass().getSimpleName().toLowerCase();
	}

	String event() {
		return event;
	}

	Link createLink(String event, String marker) {
		ComponentResources parent = resources.getContainerResources()
				.getContainerResources();
		Object[] o = context;
		if (callback != null) {
			if (o == null) {
				o = new Object[1];
			} else {
				o = new Object[o.length + 1];
				System.arraycopy(context, 0, o, 0, context.length);
			}
			o[o.length - 1] = marker;
		}
		return parent.createEventLink(event, o);
	}

	public void afterRender() {
		JSONObject spec = new JSONObject();

		spec.put("elementId", element());
		spec.put("eventType", eventType());
		if (event() != null) {
			spec.put("url", createLink(event(), contextMarker).toAbsoluteURI());
			spec.put("contextMarker", contextMarker);
		}
		spec.put("preventDefault", preventDefault);
		spec.put("hide", hide);
		spec.put("hideEffect", hideEffect);
		spec.put("hideTime", hideTime);
		if (hideOptions != null) {
			JSONObject joptions = new JSONObject();

			for (Entry<String, String> o : hideOptions.entrySet()) {
				joptions.put(o.getKey(), o.getValue());
			}
			spec.put("hideOptions", joptions);
		}
		spec.put("zoneId", zone);
		spec.put("zoneUpdate", zoneUpdate);
		spec.put("title", title);
		spec.put("history", history);
		// Does not work with AJAX JSON return
		// if ( jcontext != null ) {
		// spec.put("jcontext", new JSONLiteral(jcontext));
		// }
		spec.put("callback", callback);

		javaScriptSupport.require("tjq/bind").with(spec);
//		if (doImports) {
//			if (zoneUpdate != null) {
//				String effect = findEffect(zoneUpdate);
//				if ( effect != null ) {
//					//javaScriptSupport.importJavaScriptLibrary(assetSource.getExpandedAsset(effect));
//				}
//			}
//			if (hideEffect != null) {
//				String effect = findEffect(hideEffect);
//				if ( effect != null ) {
//					javaScriptSupport.importJavaScriptLibrary(assetSource.getExpandedAsset(effect));
//				}
//			}
//		}
//
//		javaScriptSupport.require("tjq/bind").with(spec);
	}

	private String findEffect(String effect) {
		effect = effect.toUpperCase();
		if ( effect.equals("BLIND")) {
			return EffectsConstants.BLIND;
		}
		if ( effect.equals("BOUNCE")) {
			return EffectsConstants.BOUNCE;
		}
		if ( effect.equals("CLIP")) {
			return EffectsConstants.CLIP;
		}
		if ( effect.equals("DROP")) {
			return EffectsConstants.DROP;
		}
		if ( effect.equals("EXPLODE")) {
			return EffectsConstants.EXPLODE;
		}
		if ( effect.equals("FOLD")) {
			return EffectsConstants.FOLD;
		}
		if ( effect.equals("HIGHLIGHT")) {
			return EffectsConstants.HIGHLIGHT;
		}
		if ( effect.equals("PULSATE")) {
			return EffectsConstants.PULSATE;
		}
		if ( effect.equals("SCALE")) {
			return EffectsConstants.SCALE;
		}
		if ( effect.equals("SHAKE")) {
			return EffectsConstants.SHAKE;
		}
		if ( effect.equals("SLIDE")) {
			return EffectsConstants.SLIDE;
		}
		if ( effect.equals("TRANSFER")) {
			return EffectsConstants.TRANSFER;
		}
		return null;
	}

}
