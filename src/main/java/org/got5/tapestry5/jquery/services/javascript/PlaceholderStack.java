package org.got5.tapestry5.jquery.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class PlaceholderStack implements JavaScriptStack {

	public static final String STACK_ID = "PlaceholderStack";

	private final List<Asset> javaScriptStack;

	public PlaceholderStack(
			@Symbol(SymbolConstants.PRODUCTION_MODE) final boolean productionMode,
			final AssetSource assetSource) {

		final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>() {
			public Asset map(String path) {
				return assetSource.getExpandedAsset(path);
			}
		};

		final String path = String
				.format("${assets.path}/mixins/placeholder/jquery.placeholder%s.js",
						productionMode ? ".min" : "");

		javaScriptStack = F.flow(path).map(pathToAsset).toList();
	}

	public List<String> getStacks() {
		return Collections.emptyList();
	}

	public List<Asset> getJavaScriptLibraries() {
		return javaScriptStack;
	}

	public List<StylesheetLink> getStylesheets() {
		return Collections.emptyList();
	}

	public String getInitialization() {
		return null;
	}
}
