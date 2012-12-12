package org.got5.tapestry5.jquery.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

public class DDSlickStack implements JavaScriptStack {

	public static final String STACK_ID = "DDSlickStack";
	
	private final List<Asset> javaScriptStack;
	
	private final List<StylesheetLink> cssStack;
	
	public DDSlickStack(final AssetSource assetSource) {
		super();
		
        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        javaScriptStack = F.flow("${assets.path}/components/ddslick/jquery.ddslick.min.js", 
        		"${assets.path}/components/ddslick/DdSlickComponent.js").map(pathToAsset).toList();


        final Mapper<String, StylesheetLink> pathToStylesheetLink = F.combine(pathToAsset, JQueryUtils.assetToStylesheetLink);
        cssStack = F.flow("${assets.path}/components/ddslick/DdSlickComponent.css").map(pathToStylesheetLink).toList();
	}

	public List<String> getStacks() {
		return Collections.emptyList();
	}

	public List<Asset> getJavaScriptLibraries() {
		return javaScriptStack;
	}

	public List<StylesheetLink> getStylesheets() {
		return cssStack;
	}

	public String getInitialization() {
		return null;
	}

}
