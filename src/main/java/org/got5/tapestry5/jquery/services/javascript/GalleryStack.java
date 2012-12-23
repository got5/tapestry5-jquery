package org.got5.tapestry5.jquery.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;

/**
 * Stack for the {@link Gallery} component.
 * 
 * @author criedel
 * @since 2.6.1
 */
public class GalleryStack implements JavaScriptStack {

	public static final String STACK_ID = "GalleryStack";

	private final List<Asset> javaScriptStack;

	private final List<StylesheetLink> cssStack;

	public GalleryStack(
			@Symbol(JQuerySymbolConstants.USE_MINIFIED_JS) final boolean minified,
			final AssetSource assetSource) {
		final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>() {
			public Asset map(String path) {
				return assetSource.getExpandedAsset(path);
			}
		};

		javaScriptStack = F
				.flow("${assets.path}/components/gallery/jquery.colorbox.js",
						"${assets.path}/components/gallery/gallery.js")
				.map(pathToAsset).toList();

		cssStack = Collections.emptyList();
	}

	public String getInitialization() {

		return null;
	}

	public List<Asset> getJavaScriptLibraries() {

		return javaScriptStack;
	}

	public List<StylesheetLink> getStylesheets() {

		return cssStack;
	}

	public List<String> getStacks() {

		return Collections.emptyList();
	}

}
