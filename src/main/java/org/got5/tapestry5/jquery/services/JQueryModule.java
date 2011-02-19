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

import static org.got5.tapestry5.jquery.JQuerySymbolConstants.JQUERY_CORE_PATH;
import static org.got5.tapestry5.jquery.JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME;
import static org.got5.tapestry5.jquery.JQuerySymbolConstants.JQUERY_UI_PATH;
import static org.got5.tapestry5.jquery.JQuerySymbolConstants.JQUERY_VALIDATE_PATH;
import static org.got5.tapestry5.jquery.JQuerySymbolConstants.JQUERY_VERSION;
import static org.got5.tapestry5.jquery.JQuerySymbolConstants.TAPESTRY_JQUERY_PATH;
import static org.got5.tapestry5.jquery.JQuerySymbolConstants.TAPESTRY_JS_PATH;

import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.got5.tapestry5.jquery.services.javascript.AjaxUploadStack;
import org.got5.tapestry5.jquery.services.javascript.FormFragmentSupportStack;
import org.got5.tapestry5.jquery.services.javascript.FormSupportStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryDateFieldStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryJavaScriptStack;

public class JQueryModule
{
    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration)
    {
        configuration.overrideInstance(InternalConstants.CORE_STACK_NAME, JQueryJavaScriptStack.class);
        configuration.overrideInstance("core-datefield", JQueryDateFieldStack.class);
        configuration.addInstance(FormSupportStack.STACK_ID, FormSupportStack.class);
        configuration.addInstance(FormFragmentSupportStack.STACK_ID, FormFragmentSupportStack.class);
        configuration.addInstance(AjaxUploadStack.STACK_ID, AjaxUploadStack.class);
    }

    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration)
    {
        configuration.addInstance("FormFragmentResourcesInclusionWorker", FormFragmentResourcesInclusionWorker.class);
        configuration.addInstance("FormResourcesInclusionWorker", FormResourcesInclusionWorker.class);
        configuration.addInstance("ImportJQueryUIWorker", ImportJQueryUIWorker.class, "before:Import");
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("jquery", "org.got5.tapestry5.jquery"));
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(TAPESTRY_JQUERY_PATH, "classpath:org/got5/tapestry5/jquery");
        configuration.add(TAPESTRY_JS_PATH, "classpath:org/got5/tapestry5/tapestry.js");

        configuration.add(JQUERY_CORE_PATH, "classpath:org/got5/tapestry5/jquery/jquery_core");
        configuration.add(JQUERY_VERSION, "1.5");

        configuration.add(JQUERY_UI_PATH, "classpath:org/got5/tapestry5/jquery/ui_1_8");
        configuration.add(JQUERY_UI_DEFAULT_THEME, "classpath:org/got5/tapestry5/jquery/themes/ui-lightness/jquery-ui-1.8.custom.css");

        configuration.add(JQUERY_VALIDATE_PATH, "classpath:org/got5/tapestry5/jquery/validate/1_7");
    }

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("tap-jquery", "org/got5/tapestry5");
    }

}
