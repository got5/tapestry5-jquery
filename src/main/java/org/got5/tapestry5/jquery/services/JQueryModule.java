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

package org.got5.tapestry5.jquery.services;



import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.got5.tapestry5.jquery.JQueryComponentConstants;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.JQueryVersion;
import org.got5.tapestry5.jquery.services.impl.WidgetParamsImpl;
import org.got5.tapestry5.jquery.services.javascript.AjaxUploadStack;
import org.got5.tapestry5.jquery.services.javascript.FormFragmentSupportStack;
import org.got5.tapestry5.jquery.services.javascript.FormSupportStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryDateFieldStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryJavaScriptStack;

public class JQueryModule
{	
    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration,
    		@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE)
            boolean suppressPrototype)
    {
    	
    	configuration.overrideInstance(InternalConstants.CORE_STACK_NAME, JQueryJavaScriptStack.class);
    	if(suppressPrototype)
    	{	
    		configuration.overrideInstance("core-datefield", JQueryDateFieldStack.class);
    		configuration.addInstance(FormSupportStack.STACK_ID, FormSupportStack.class);
    		configuration.addInstance(FormFragmentSupportStack.STACK_ID, FormFragmentSupportStack.class);
    	}
    	configuration.addInstance(AjaxUploadStack.STACK_ID, AjaxUploadStack.class);
    }

    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration,
    		@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE)
            boolean suppressPrototype)
    {
    	if(suppressPrototype)
    	{	
    		configuration.addInstance("FormFragmentResourcesInclusionWorker", FormFragmentResourcesInclusionWorker.class);
    		configuration.addInstance("FormResourcesInclusionWorker", FormResourcesInclusionWorker.class);
    	}
    	configuration.addInstance("ImportJQueryUIWorker", ImportJQueryUIWorker.class, "before:Import");
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("jquery", "org.got5.tapestry5.jquery"));
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(JQuerySymbolConstants.TAPESTRY_JQUERY_PATH, "classpath:org/got5/tapestry5/jquery");
        configuration.add(JQuerySymbolConstants.TAPESTRY_JS_PATH, "classpath:org/got5/tapestry5/tapestry.js");

        configuration.add(JQuerySymbolConstants.JQUERY_CORE_PATH, "classpath:org/got5/tapestry5/jquery/jquery_core");
        configuration.add(JQuerySymbolConstants.JQUERY_VERSION, JQueryVersion.v1_5_0);

        configuration.add(JQuerySymbolConstants.JQUERY_UI_PATH, "classpath:org/got5/tapestry5/jquery/ui_1_8");
        configuration.add(JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME, "classpath:org/got5/tapestry5/jquery/themes/ui-lightness/jquery-ui-1.8.custom.css");

        configuration.add(JQuerySymbolConstants.JQUERY_VALIDATE_PATH, "classpath:org/got5/tapestry5/jquery/validate/1_7");
        configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "true");
        configuration.add(JQuerySymbolConstants.JQUERY_ALIAS, "$");
        
        configuration.add(JQuerySymbolConstants.ASSETS_PATH, "classpath:org/got5/tapestry5/jquery/assets");
        
        configuration.add(JQueryComponentConstants.CUSTOM_DATEPICKER_PARAMS, new JSONObject().toString());
    
    }

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("tap-jquery", "org/got5/tapestry5");
    }
 public static void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration,
    		@InjectService("SelectorBindingFactory")
    		BindingFactory selectorBindingFactory
    		) {
        configuration.add("selector", selectorBindingFactory);
}
public static void bind(ServiceBinder binder)
    {
      binder.bind(WidgetParams.class, WidgetParamsImpl.class);
      binder.bind(BindingFactory.class,SelectorBindingFactory.class).withId("SelectorBindingFactory");
    }
    
    
    @Contribute(TypeCoercer.class)
    public static void provideBasicTypeCoercions(Configuration<CoercionTuple> configuration)
    {
    	configuration.add(new CoercionTuple<String, JSONObject>(String.class, JSONObject.class, new Coercion<String, JSONObject>() {


			public JSONObject coerce(String input) {
				return new JSONObject(input);
			}
		}));
    }


}
