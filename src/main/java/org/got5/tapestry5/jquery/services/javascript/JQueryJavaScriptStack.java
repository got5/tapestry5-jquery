package org.got5.tapestry5.jquery.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.internal.services.javascript.CoreJavaScriptStack;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * Replacement for {@link CoreJavaScriptStack}.
 *
 * @author criedel
 */
public class JQueryJavaScriptStack implements JavaScriptStack {

    private final boolean productionMode;

    private final List<Asset> javaScriptStack;

    private final List<StylesheetLink> stylesheetStack;

    public JQueryJavaScriptStack(@Symbol(SymbolConstants.PRODUCTION_MODE)
                                 final boolean productionMode,

                                 final AssetSource assetSource)
    {
        this.productionMode = productionMode;

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            @Override
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        final Mapper<String, StylesheetLink> pathToStylesheetLink = pathToAsset.combine(JQueryUtils.assetToStylesheetLink);

        stylesheetStack = F.flow("${tapestry.default-stylesheet}",
                                 "${jquery.ui.default-theme.path}")
                           .map(pathToStylesheetLink)
                           .toList();

        if (productionMode) {

            javaScriptStack = F
                .flow(  "${tapestry.js.path}",
                        "${jquery.core.path}/jquery-${jquery.version}.min.js",
                        "${jquery.ui.path}/minified/jquery.ui.core.min.js",
                        "${jquery.ui.path}/minified/jquery.ui.position.min.js",
                        "${jquery.ui.path}/minified/jquery.ui.widget.min.js",
                        "${jquery.ui.path}/minified/jquery.effects.core.min.js",
                        "${jquery.ui.path}/minified/jquery.effects.highlight.min.js",
                        "${tapestry.jquery.path}/tapestry-jquery.js")
            .map(pathToAsset).toList();

        } else {

            javaScriptStack = F
                .flow(  "${tapestry.js.path}",
                        "${jquery.core.path}/jquery-${jquery.version}.js",
                        "${jquery.ui.path}/jquery.ui.core.js",
                        "${jquery.ui.path}/jquery.ui.position.js",
                        "${jquery.ui.path}/jquery.ui.widget.js",
                        "${jquery.ui.path}/jquery.effects.core.js",
                        "${jquery.ui.path}/jquery.effects.highlight.js",
                        "${tapestry.jquery.path}/tapestry-jquery.js")
            .map(pathToAsset).toList();

        }

    }

    public String getInitialization()
    {
        return productionMode ? null : "Tapestry.DEBUG_ENABLED = true;";
    }

    public List<Asset> getJavaScriptLibraries()
    {
        return javaScriptStack;
    }

    public List<StylesheetLink> getStylesheets()
    {
        return stylesheetStack;
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }

}
