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
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.ApplicationStateContribution;
import org.apache.tapestry5.services.ApplicationStateCreator;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.test.data.IDataSource;
import org.got5.tapestry5.jquery.test.data.MockDataSource;
import org.got5.tapestry5.jquery.services.JQueryModule;

@SubModule(value = JQueryModule.class)
public class AppModule
{
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
    	configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,fr,de");
    	
    	configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    	
    	configuration.add(SymbolConstants.COMBINE_SCRIPTS, "false");
    	
    	configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
        
    	configuration.add(SymbolConstants.GZIP_COMPRESSION_ENABLED, "false");
    	
    	//configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "false");
    		
    	//configuration.add(JQuerySymbolConstants.JQUERY_ALIAS, "$j");
    	
    	configuration.add("demo-src-dir","");
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
}
