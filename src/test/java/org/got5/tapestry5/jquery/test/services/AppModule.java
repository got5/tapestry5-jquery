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
package org.got5.tapestry5.jquery.test.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationStateContribution;
import org.apache.tapestry5.services.ApplicationStateCreator;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.got5.tapestry5.jquery.EffectsConstants;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.EffectsParam;
import org.got5.tapestry5.jquery.services.JQueryModule;
import org.got5.tapestry5.jquery.services.WidgetParams;
import org.got5.tapestry5.jquery.test.data.IDataSource;
import org.got5.tapestry5.jquery.test.data.MockDataSource;
import org.got5.tapestry5.jquery.test.pages.GAnalyticsScriptsInjector;

@SubModule(value = JQueryModule.class)
public class AppModule
{
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
    	configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,fr,de");
    	
    	configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
    	
    	configuration.add(SymbolConstants.COMBINE_SCRIPTS, "false");
    	
    	configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
        
    	configuration.add(SymbolConstants.GZIP_COMPRESSION_ENABLED, "false");
    	
    	configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "true");
    	
    	configuration.add(JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME, "context:css/south-street/jquery-ui.css");
    	
    	configuration.add("demo-src-dir","");
    	
    }
    
@Contribute(WidgetParams.class)
public void addWidgetParams(MappedConfiguration<String, JSONObject> configuration){
	configuration.add("slider", new JSONObject().put("min", 5));
    configuration.add("customdatepicker", new JSONObject("prevText","Previous Month"));
}
    
    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("demo-jquery", "static/css");
    }
    
    public void contributeApplicationStateManager(
			MappedConfiguration<Class, ApplicationStateContribution> configuration) {

		ApplicationStateCreator<IDataSource> creator = new ApplicationStateCreator<IDataSource>() {
			public IDataSource create() {
				return new MockDataSource();
			}
		};

		configuration.add(IDataSource.class, new ApplicationStateContribution(
				"session", creator));
	}
    
@Contribute(EffectsParam.class)
public void addEffectsFile(Configuration<String> configuration){
	configuration.add(EffectsConstants.SHAKE);
}

public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration,
		@Symbol(SymbolConstants.PRODUCTION_MODE) final boolean productionMode) {

	if (productionMode) {
		configuration.addInstance("GAnalyticsScript", GAnalyticsScriptsInjector.class, "after:DocumentLinker");
	}

}
}
