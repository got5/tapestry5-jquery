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

    public JQueryJavaScriptStack(@Symbol(SymbolConstants.PRODUCTION_MODE) boolean productionMode,
                                 final AssetSource assetSource) {

        this.productionMode = productionMode;

        Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            @Override
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        Mapper<String, StylesheetLink> pathToStylesheetLink = pathToAsset.combine(JQueryUtils.assetToStylesheetLink);

        stylesheetStack = F.flow("${tapestry.default-stylesheet}",
                                 "${tapestry.jquery.path}/themes/ui-lightness/jquery-ui-1.8.custom.css")
                           .map(pathToStylesheetLink)
                           .toList();
        
        if (productionMode) {

            javaScriptStack = F
                .flow(  "org/got5/tapestry5/tapestry.js",
                        "${tapestry.jquery.path}/jquery_1_4_2/jquery-1.4.2.min.js", 
                        "${tapestry.jquery.path}/ui_1_8/minified/jquery.ui.core.min.js",
                        "${tapestry.jquery.path}/ui_1_8/minified/jquery.ui.position.min.js", 
                        "${tapestry.jquery.path}/ui_1_8/minified/jquery.ui.widget.min.js",
                        "${tapestry.jquery.path}/ui_1_8/minified/jquery.effects.core.min.js", 
                        "${tapestry.jquery.path}/tapestry-jquery.js")
            .map(pathToAsset).toList();

        } else {
            
            javaScriptStack = F
                .flow(  "org/got5/tapestry5/tapestry.js",
                        "${tapestry.jquery.path}/jquery_1_4_2/jquery-1.4.2.js", 
                        "${tapestry.jquery.path}/ui_1_8/jquery.ui.core.js",
                        "${tapestry.jquery.path}/ui_1_8/jquery.ui.position.js", 
                        "${tapestry.jquery.path}/ui_1_8/jquery.ui.widget.js",
                        "${tapestry.jquery.path}/ui_1_8/jquery.effects.core.js", 
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
