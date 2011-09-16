//
// Copyright 2010 GOT5 (GO Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.got5.tapestry5.jquery.services.javascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.services.javascript.CoreJavaScriptStack;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.EffectsParam;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * Replacement for {@link CoreJavaScriptStack}.
 *
 * @author criedel, GOT5
 */
public class JQueryJavaScriptStack implements JavaScriptStack {

    private final boolean productionMode;
    
    private String jQueryAlias;
    
    private final boolean suppressPrototype;

    private final List<Asset> jQueryJsStack;
    
    private final List<StylesheetLink> jQueryCssStack;
    
    private final AssetSource assetSource;
      
    private final JavaScriptStackSource jsStackSource;

    private SymbolSource symbolSource;

    private EffectsParam effectsParam;

    public JQueryJavaScriptStack(@Symbol(SymbolConstants.PRODUCTION_MODE)
                                 final boolean productionMode,
                                 
                                 @Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
                                 final String jQueryAlias,
                                 
                                 @Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE)
                                 final boolean suppressPrototype,

                                 final AssetSource assetSource, 
                   
                                 final JavaScriptStackSource jsStackSrc,

    							 final SymbolSource symbolSource, 
    							 
    							 final EffectsParam effectsParam)

    {
    	
        this.productionMode = productionMode;
        this.suppressPrototype = suppressPrototype;
        this.assetSource = assetSource;
        this.jQueryAlias = jQueryAlias;
        this.jsStackSource = jsStackSrc;
        this.symbolSource = symbolSource;
        this.effectsParam = effectsParam;


        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            public Asset map(String path)
            {
            	if(productionMode){
            		
            		String pathMin = symbolSource.expandSymbols(path);
            		
            		if(path.equalsIgnoreCase("${jquery.core.path}")){
            			path = new StringBuffer(pathMin).insert(pathMin.lastIndexOf(".js"), ".min").toString();
            		}
            		else if(path.contains("${jquery.ui.path}")){
            			path = new StringBuffer(pathMin).insert(pathMin.lastIndexOf(".js"), ".min")
            											.insert(pathMin.lastIndexOf('/'), "/minified").toString();
            		}
            	}
            	
                return assetSource.getExpandedAsset(path);
            }
        };

        final Mapper<String, StylesheetLink> pathToStylesheetLink = F.combine(pathToAsset, JQueryUtils.assetToStylesheetLink);

        jQueryCssStack = F.flow("${jquery.ui.default-theme.path}")
                           .map(pathToStylesheetLink)
                           .toList();

       	jQueryJsStack = F
                .flow(  "${jquery.core.path}",
                        "${jquery.ui.path}/jquery.ui.core.js",
                        "${jquery.ui.path}/jquery.ui.position.js",
                        "${jquery.ui.path}/jquery.ui.widget.js",
                        "${jquery.ui.path}/jquery.effects.core.js",
                        "${tapestry.jquery.path}/jquery.json-2.2.js")
            .concat(F.flow(this.effectsParam.getEffectsToLoad())).map(pathToAsset).toList();

    }
    
    public String getInitialization()
    {
    	if(!suppressPrototype && jQueryAlias.equals("$")) jQueryAlias="$j";
        return productionMode ? "var "+jQueryAlias+" = jQuery;" : "var "+jQueryAlias+" = jQuery; Tapestry.DEBUG_ENABLED = true; var selector = new Array();";
    }
    
    /**
     * Asset in Prototype, have to be changed by a jQuery version
     * 
     * JavaScript File          | A Prototype JavaScript File ? |  jQuery version exist ?
     * t5-ajax.js				|Y
     * t5-alerts.js				|Y								| t5-alerts-jquery.js
     * t5-console.js			|Y								| t5-console-jquery.js 
     * t5-core.js				|N
     * t5-dom.js				|Y								| t5-dom-jquery.js
     * t5-events.js				|N
     * t5-formfragment.js		|Y
     * t5-init.js				|N
     * t5-prototype.js			|Y								| t5-jquery.js						
     * t5-pubsub.js				|I do not think so
     * t5-spi.js				|N
     * tapestry-console.js		|N
     * tapestry.js				|Y								| tapestry-jquery.js : has to be reviewed !!
     * tree.js					|Y
     */
    public Object chooseJavascript(Asset asset){
    	
    	
    	
    	if(suppressPrototype)
    	{
    		if(asset.getResource().getFile().endsWith("t5-prototype.js"))
    		{
    			return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-jquery.js");
    		}
    		
    		if(asset.getResource().getFile().endsWith("tapestry.js"))
    		{
    			return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/tapestry-jquery.js");
    		}
    		if(asset.getResource().getFile().endsWith("t5-console.js"))
    		{
    			return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-console-jquery.js");
    		}
    		if(asset.getResource().getFile().endsWith("t5-dom.js"))
    		{
    			return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-dom-jquery.js");
    		}
    		if(asset.getResource().getFile().endsWith("t5-alerts.js"))
    		{
    			return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-alerts-jquery.js");
    		}
    		if(asset.getResource().getFile().endsWith("prototype.js") || 
    				asset.getResource().getFile().endsWith("scriptaculous.js") ||
    				asset.getResource().getFile().endsWith("effects.js"))
    		{
    			return null;
    		}
    		
    	}
    	
    	return asset;
    }
    
    public List<Asset> getJavaScriptLibraries()
    {
    	List<Asset> ret = new ArrayList<Asset>();
    	
    	if(suppressPrototype)
    	{
    		ret.add(this.assetSource.getExpandedAsset("${tapestry.js.path}"));
    	}
    	
    	ret.addAll(jQueryJsStack);
    	
    	if(!suppressPrototype){
    		ret.add(this.assetSource.getExpandedAsset("${tapestry.jquery.path}/noconflict.js"));
    	}
    	
    	for(Asset asset : jsStackSource.getStack(JQuerySymbolConstants.PROTOTYPE_STACK).getJavaScriptLibraries())
    	{
    		asset=(Asset) chooseJavascript(asset);
    		if(asset!=null) ret.add(asset);
    	}
    	
    	if(!suppressPrototype){
    		ret.add(this.assetSource.getExpandedAsset("${tapestry.jquery.path}/jquery-noconflict.js"));
    	}
  
    	return ret;
        
    }

    public List<StylesheetLink> getStylesheets()
    {
    	List<StylesheetLink> ret = new ArrayList<StylesheetLink>();
    	
    	ret.addAll(jQueryCssStack);
    	if(!suppressPrototype)
    	{
     		ret.addAll(jsStackSource.getStack(JQuerySymbolConstants.PROTOTYPE_STACK).getStylesheets());
    	}
    	else
    	{
    		for(StylesheetLink css : jsStackSource.getStack(JQuerySymbolConstants.PROTOTYPE_STACK).getStylesheets()){
    			if(css.getURL().endsWith("t5-alerts.css") || 
    					css.getURL().endsWith("tree.css")) ret.add(css);
    		}
    	}
 		return ret;
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }

}
