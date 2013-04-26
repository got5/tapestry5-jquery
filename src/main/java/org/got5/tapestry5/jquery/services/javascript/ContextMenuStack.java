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
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * Resource stack for {@link ContextMenu}.
 */
public class ContextMenuStack implements JavaScriptStack {
	
	public static final String STACK_ID = "ContextMenuStack";
	
	private final List<Asset> javaScriptStack;
	
	private final List<StylesheetLink> cssStack;
	
	public ContextMenuStack(@Symbol(JQuerySymbolConstants.USE_MINIFIED_JS) final boolean productionMode,
			final AssetSource assetSource)
    {
		final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>() {
			public Asset map(String path) {
				return assetSource.getExpandedAsset(path);
			}
		};
		
		final String contextMenuJquery = String.format("${assets.path}/mixins/contextmenu/jquery.contextMenu%s.js", productionMode ? ".min" : "");
        final String contextMenuJS = String.format("${assets.path}/mixins/contextmenu/contextmenu.js");

        javaScriptStack = F.flow(contextMenuJquery, contextMenuJS).map(pathToAsset).toList();
	
		final Mapper<String, StylesheetLink> pathToStylesheetLink = F.combine(pathToAsset, JQueryUtils.assetToStylesheetLink);
        cssStack = F.flow("${assets.path}/mixins/contextmenu/jquery.contextMenu.css").map(pathToStylesheetLink).toList();
    }
	
	public String getInitialization()
    {
        return null;
    }
    
    public List<Asset> getJavaScriptLibraries()
    {
    	return javaScriptStack;
    }
    
    public List<StylesheetLink> getStylesheets()
    {
        return cssStack;
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }
}
