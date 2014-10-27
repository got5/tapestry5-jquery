//
// Copyright 2010 GOT5 (GO Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.got5.tapestry5.jquery.services.javascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.internal.services.javascript.CoreJavaScriptStack;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.EffectsParam;
import org.got5.tapestry5.jquery.services.JavaScriptFilesConfiguration;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * Replacement for {@link CoreJavaScriptStack}.
 * 
 * @author criedel, GOT5
 */
public class JQueryJavaScriptStack implements JavaScriptStack {

	private final boolean minified;

	private final boolean suppressPrototype;

	private final boolean mouseWheel;
	
	private final boolean excludeJsCore;

	private String jQueryAlias;

	private final List<Asset> jQueryJsStack;

	private final AssetSource assetSource;

	private final JavaScriptStackSource jsStackSource;

	private EffectsParam effectsParam;

	private JavaScriptFilesConfiguration jsConf;

	public JQueryJavaScriptStack(
			@Symbol(JQuerySymbolConstants.USE_MINIFIED_JS) final boolean minified,

			@Symbol(JQuerySymbolConstants.JQUERY_ALIAS) final String jQueryAlias,

			@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE) final boolean suppressPrototype,

			@Symbol(JQuerySymbolConstants.ADD_MOUSEWHEEL_EVENT) final boolean mouseWheel,
			
			@Symbol(JQuerySymbolConstants.EXCLUDE_CORE_JS_STACK) final boolean excludeJsCore,

			final AssetSource assetSource,

			final JavaScriptStackSource jsStackSrc,

			final SymbolSource symbolSource,

			final EffectsParam effectsParam,

			final JavaScriptFilesConfiguration jsConf)

	{

		this.minified = minified;
		this.suppressPrototype = suppressPrototype;
		this.mouseWheel = mouseWheel;
		this.excludeJsCore= excludeJsCore;

		this.assetSource = assetSource;
		this.jQueryAlias = jQueryAlias;
		this.jsStackSource = jsStackSrc;
		this.effectsParam = effectsParam;
		this.jsConf = jsConf;

		final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>() {
			public Asset map(String path) {
				if (minified) {

					String pathMin = symbolSource.expandSymbols(path);

					if (path.equalsIgnoreCase("${jquery.core.path}")) {
						path = new StringBuffer(pathMin).insert(
								pathMin.lastIndexOf(".js"), ".min").toString();
					} else if (path.contains("${jquery.ui.path}")) {
						path = new StringBuffer(pathMin)
								.insert(pathMin.lastIndexOf(".js"), ".min")
								.insert(pathMin.lastIndexOf('/'), "/minified")
								.toString();
					}
				}

				return assetSource.getExpandedAsset(path);
			}
		};

		final Mapper<String, StylesheetLink> pathToStylesheetLink = F.combine(
				pathToAsset, JQueryUtils.assetToStylesheetLink);

		jQueryJsStack = F
				.flow("${jquery.core.path}",
						"${jquery.ui.path}/jquery.ui.core.js",
						"${jquery.ui.path}/jquery.ui.position.js",
						"${jquery.ui.path}/jquery.ui.widget.js",
						"${jquery.ui.path}/jquery.effects.core.js",
						"${tapestry.jquery.path}/jquery.json-2.2.js")
				.concat(F.flow(this.effectsParam.getEffectsToLoad()))
				.map(pathToAsset).toList();

	}

	public String getInitialization() {
		if (!suppressPrototype && (jQueryAlias.equals("$") || jQueryAlias.equals("jQuery")))
			
			//If the alias='jQuery', we can have some problem with IE ! 
			throw new RuntimeException(
					"You are using an application based on Prototype"
							+ " and jQuery. You should set in your AppModule the alias for the jQuery object to a different"
							+ " value than '$' and 'jQuery'");
	
		return minified ? "var " + jQueryAlias + " = jQuery; Tapestry.JQUERY="
				+ suppressPrototype + ";"
				: "var "
						+ jQueryAlias
						+ " = jQuery; Tapestry.DEBUG_ENABLED = true; var selector = new Array(); Tapestry.JQUERY="
						+ suppressPrototype + ";";
	}

	/**
	 * Asset in Prototype, have to be changed by a jQuery version
	 */
	public Object chooseJavascript(Asset asset) {
		return suppressPrototype ? jsConf.getAsset(asset) : asset;
	}

	public List<Asset> getJavaScriptLibraries() {
		
		
		List<Asset> ret = new ArrayList<Asset>();
		
		if (this.excludeJsCore){
			return ret;
		}

		if (suppressPrototype) {
			ret.add(this.assetSource.getExpandedAsset("${tapestry.js.path}"));
		}

		ret.addAll(jQueryJsStack);

		if (!suppressPrototype) {
			ret.add(this.assetSource
					.getExpandedAsset("${tapestry.jquery.path}/noconflict.js"));
		}

		for (Asset asset : jsStackSource.getStack(
				JQuerySymbolConstants.PROTOTYPE_STACK).getJavaScriptLibraries()) {
			asset = (Asset) chooseJavascript(asset);
			if (asset != null)
				ret.add(asset);
		}

		if (!suppressPrototype) {
			ret.add(this.assetSource
					.getExpandedAsset("${tapestry.jquery.path}/jquery-noconflict.js"));
		}

		if (mouseWheel)
			ret.add(this.assetSource
					.getExpandedAsset("${tapestry.jquery.path}/jquery_widgets/jquery.mousewheel.js"));
		return ret;

	}

	public List<StylesheetLink> getStylesheets() {
		List<StylesheetLink> ret = new ArrayList<StylesheetLink>();

		if (!suppressPrototype) {
			ret.addAll(jsStackSource.getStack(
					JQuerySymbolConstants.PROTOTYPE_STACK).getStylesheets());
		} else {
			for (StylesheetLink css : jsStackSource.getStack(
					JQuerySymbolConstants.PROTOTYPE_STACK).getStylesheets()) {
				if (css.getURL().endsWith("t5-alerts.css")
						|| css.getURL().endsWith("tapestry-console.css")
						|| css.getURL().endsWith("tree.css"))
					ret.add(css);
			}
		}
		return ret;
	}

	public List<String> getStacks() {
		return Collections.emptyList();
	}

}
