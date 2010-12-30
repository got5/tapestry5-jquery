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

public class FormSupportStack implements JavaScriptStack {

    public static final String STACK_ID = "FormSupportStack";
    
    private final List<Asset> javaScriptStack;

    private final List<StylesheetLink> stylesheetStack;
    
    public FormSupportStack(final AssetSource assetSource) {

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            @Override
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        javaScriptStack = F.flow("${tapestry.jquery.path}/jquery.validate.min.js", 
                                 "${tapestry.jquery.path}/validation.js")
                           .map(pathToAsset).toList();

        final Mapper<String, StylesheetLink> pathToStylesheetLink = pathToAsset.combine(JQueryUtils.assetToStylesheetLink);

        stylesheetStack = F.flow("${tapestry.jquery.path}/form.css").map(pathToStylesheetLink).toList();
        
    }

    public List<String> getStacks() {

        return Collections.emptyList();
    }

    public List<Asset> getJavaScriptLibraries() {

        return javaScriptStack;
    }

    public List<StylesheetLink> getStylesheets() {

        return stylesheetStack;
    }

    public String getInitialization() {

        return null;
    }

}
