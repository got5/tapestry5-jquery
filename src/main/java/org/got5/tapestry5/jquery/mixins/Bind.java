package org.got5.tapestry5.jquery.mixins;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = { "${assets.path}/mixins/bind/bind.js" })
public class Bind {

	@Parameter(defaultPrefix = "literal")
	private String element;

	@Parameter(defaultPrefix = "literal")
	private String event;

	@Parameter(defaultPrefix = "literal")
	private String hide;

	@Parameter(defaultPrefix = "literal", value = "slide")
	private String hideEffect;

	@Parameter(defaultPrefix = "literal", value = "500")
	private String hideTime;

	@Parameter
	private Map<String, String> hideOptions;

	@Parameter(defaultPrefix = "literal")
	private String zone;

	@Parameter(defaultPrefix = "literal", value = "highlight")
	private String zoneUpdate;

	@Parameter(defaultPrefix = "literal")
	private String callback;

	@Parameter
	private Object[] context;

	@Parameter(defaultPrefix = "literal")
	private String title;

	@Parameter(defaultPrefix = "literal")
	private String eventType;

	@Parameter(value = "true")
	private Boolean preventDefault;

	@Parameter(value = "true")
	private Boolean doImports;

	@Parameter(value = "CoNtExT", defaultPrefix = "literal")
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
		// Does not work with AJAX JSON return
		// if ( jcontext != null ) {
		// spec.put("jcontext", new JSONLiteral(jcontext));
		// }
		spec.put("callback", callback);
		javaScriptSupport.addInitializerCall("jqbind", spec);
		if (doImports) {
			if (zoneUpdate != null) {
				javaScriptSupport
						.importJavaScriptLibrary(assetSource.getClasspathAsset(String
								.format("/org/got5/tapestry5/jquery/ui_1_8/jquery.effects.%s.js",
										zoneUpdate)));
			}
			if (hideEffect != null) {
				javaScriptSupport
						.importJavaScriptLibrary(assetSource.getClasspathAsset(String
								.format("/org/got5/tapestry5/jquery/ui_1_8/jquery.effects.%s.js",
										hideEffect)));
			}
		}
	}
}
