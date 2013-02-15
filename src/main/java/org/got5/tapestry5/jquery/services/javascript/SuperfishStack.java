package org.got5.tapestry5.jquery.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.got5.tapestry5.jquery.components.Superfish;

/**
 * Resource stack for {@link Superfish}.
 *
 * @author Emmanuel DEMEY
 */
public class SuperfishStack implements JavaScriptStack {

	public static final String STACK_ID = "SuperfishStack";

	private final List<Asset> javaScriptStack;

	public SuperfishStack(final AssetSource assetSource) {
		super();

		final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>() {
			public Asset map(String path) {
				return assetSource.getExpandedAsset(path);
			}
		};

		javaScriptStack = F
				.flow("${assets.path}/components/superfish/js/jquery.hoverIntent.js",
						"${assets.path}/components/superfish/js/jquery.bgiframe.min.js",
						"${assets.path}/components/superfish/js/superfish.js",
						"${assets.path}/components/superfish/js/supersubs.js",
						"${assets.path}/components/superfish/js/mySuperfish.js")
				.map(pathToAsset).toList();

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
