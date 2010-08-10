package org.got5.tapestry5.jquery.services.javascript;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class FormFragmentSupportStack implements JavaScriptStack {

    public static final String STACK_ID = "FormFragmentSupportStack";

    private final List<Asset> javaScriptStack;

    public FormFragmentSupportStack(final AssetSource assetSource) {

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>() {

            @Override
            public Asset map(String path) {

                return assetSource.getExpandedAsset(path);
            }
        };

        javaScriptStack = F.flow("${tapestry.jquery.path}/components/formfragment.js", 
                                 "${tapestry.jquery.path}/ui_1_8/minified/jquery.effects.blind.min.js")
                           .map(pathToAsset).toList();

    }

    public List<String> getStacks() {

        return Arrays.asList(new String[] { FormSupportStack.STACK_ID });
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
