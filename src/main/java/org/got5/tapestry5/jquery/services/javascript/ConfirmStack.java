package org.got5.tapestry5.jquery.services.javascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class ConfirmStack implements JavaScriptStack 
{
	public static final String STACK_ID = "ConfirmStack";
	
	private final AssetSource assetSource;
	
	public String getInitialization()
    {
        return null;
    }
    
    public List<Asset> getJavaScriptLibraries()
    {
    	final List<Asset> javaScriptStack = new ArrayList<Asset>();

        javaScriptStack.add(assetSource.getExpandedAsset("${assets.path}/mixins/confirm/confirm.js"));
        
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
    
    public ConfirmStack(final AssetSource assetSource)
    {
        this.assetSource = assetSource;
    }

}
