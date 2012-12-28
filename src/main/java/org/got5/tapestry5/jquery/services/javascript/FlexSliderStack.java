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

public class FlexSliderStack implements JavaScriptStack{

	public static final String STACK_ID = "FlexSliderStack";

    private final List<Asset> javaScriptStack;

    private final List<StylesheetLink> cssStack;

    public FlexSliderStack(
            @Symbol(JQuerySymbolConstants.USE_MINIFIED_JS)
            final boolean minified,
            final AssetSource assetSource)
    {

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        final String path = String.format("${assets.path}/components/flexslider/jquery.flexslider%s.js", minified ? "-min" : "");

        javaScriptStack = F.flow(path, "${assets.path}/components/flexslider/flexslider.js").map(pathToAsset).toList();


        final Mapper<String, StylesheetLink> pathToStylesheetLink = F.combine(pathToAsset, JQueryUtils.assetToStylesheetLink);
        cssStack = F.flow("${assets.path}/components/flexslider/flexslider.css").map(pathToStylesheetLink).toList();
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
