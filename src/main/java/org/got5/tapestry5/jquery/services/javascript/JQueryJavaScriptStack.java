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
import org.apache.tapestry5.services.ClientInfrastructure;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
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
	
	private final ClientInfrastructure clientInfrastructure;

    private final boolean productionMode;
    
    private String jQueryAlias;
    
    private final boolean suppressPrototype;

    private final List<Asset> jQueryJsStack;
    
    private final List<StylesheetLink> jQueryCssStack;
    
    private final AssetSource assetSource;
    
    private SymbolSource symbolSource;

    private EffectsParam effectsParam;

	private final boolean mousewheel;



    public JQueryJavaScriptStack(ClientInfrastructure clientInfrastructure,
    		
    							 @Symbol(SymbolConstants.PRODUCTION_MODE)
                                 final boolean productionMode,
                                 
                                 @Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
                                 final String jQueryAlias,
                                 
                                 @Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE)
                                 final boolean suppressPrototype,

                                 final AssetSource assetSource, 

    							 final SymbolSource symbolSource, 
    							 
    							 final EffectsParam effectsParam, 
    							 
    							 @Symbol(JQuerySymbolConstants.ADD_MOUSEWHEEL_EVENT)
    							final boolean mousewheel)
    {
    	this.clientInfrastructure = clientInfrastructure;
        this.productionMode = productionMode;
        this.suppressPrototype = suppressPrototype;
        this.assetSource = assetSource;
        this.jQueryAlias = jQueryAlias;
        this.symbolSource = symbolSource;
        this.effectsParam = effectsParam;
        this.mousewheel = mousewheel;

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            @Override
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

        final Mapper<String, StylesheetLink> pathToStylesheetLink = pathToAsset.combine(JQueryUtils.assetToStylesheetLink);

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
        return productionMode ? "var "+jQueryAlias+" = jQuery; Tapestry.JQUERY="+suppressPrototype+";" : "var "+jQueryAlias+" = jQuery; Tapestry.DEBUG_ENABLED = true; var selector = new Array(); Tapestry.JQUERY="+suppressPrototype+";";
    }

    public List<Asset> getJavaScriptLibraries()
    {
    	List<Asset> ret = new ArrayList<Asset>();
    	if(suppressPrototype)
    	{
    		String pathToTapestryJs = "${tapestry.js.path}";
    	    Asset  tapestryJs = this.assetSource.getExpandedAsset(pathToTapestryJs);
    	    ret.add(tapestryJs);
    	
    	    ret.addAll(jQueryJsStack);
    		
    	    String pathToTapestryJqueryJs = "${tapestry.jquery.path}/tapestry-jquery.js";
    	    Asset  tapestryJqueryJs = this.assetSource.getExpandedAsset(pathToTapestryJqueryJs);
    		ret.add(tapestryJqueryJs);
    		
    		for(Asset a : clientInfrastructure.getJavascriptStack()){
    			if(a.toClientURL().contains("tapestry-message")){ret.add(a);}
    		}
    	}
    	else
    	{
    		ret.addAll(jQueryJsStack);
    		
    		String pathToTapestryJqueryJs = "${tapestry.jquery.path}/noconflict.js";
    		Asset  tapestryJqueryJs = this.assetSource.getExpandedAsset(pathToTapestryJqueryJs);
    		ret.add(tapestryJqueryJs);
    		
    		ret.addAll(clientInfrastructure.getJavascriptStack());   	
    		
    		pathToTapestryJqueryJs = "${tapestry.jquery.path}/jquery-noconflict.js";
    		tapestryJqueryJs = this.assetSource.getExpandedAsset(pathToTapestryJqueryJs);
    		ret.add(tapestryJqueryJs);
    	}	
 		
    	if(mousewheel)
    		ret.add(this.assetSource.getExpandedAsset("${tapestry.jquery.path}/jquery_widgets/jquery.mousewheel.js"));
    	
    	
 		return ret;
        
    }

    public List<StylesheetLink> getStylesheets()
    {
    	List<StylesheetLink> ret = new ArrayList<StylesheetLink>();
    	
    	ret.addAll(jQueryCssStack);
    	if(!suppressPrototype)
    	{
    		List<StylesheetLink> prototypeCssStack = F.flow(clientInfrastructure.getStylesheetStack()).map(TapestryInternalUtils.assetToStylesheetLink)
            .toList();
    		ret.addAll(prototypeCssStack);  
    	}	
 		return ret;
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }

}
