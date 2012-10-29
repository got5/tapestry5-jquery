package org.got5.tapestry5.jquery.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.got5.tapestry5.jquery.components.InPlaceEditor;

/**
 * Resource stack for {@link InPlaceEditor}.
 *
 * @author Emmanuel DEMEY
 */
public class InPlaceEditorStack implements JavaScriptStack
{
    public static final String STACK_ID = "InPlaceEditorStack";

    private final List<Asset> javaScriptStack;

    public InPlaceEditorStack(
            final AssetSource assetSource)
    {

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        javaScriptStack = F.flow("${assets.path}/components/jeditable/jquery.jeditable.js",
        		"${assets.path}/components/jeditable/jeditable.js").map(pathToAsset).toList();
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
    	return Collections.emptyList();
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }

}
