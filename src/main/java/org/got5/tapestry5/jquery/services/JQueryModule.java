//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
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

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.LibraryMapping;
import org.got5.tapestry5.clientresources.ClientResourcesConstants;
import org.got5.tapestry5.clientresources.services.AssetPathStack;
import org.got5.tapestry5.jquery.JQueryClientResourcesConstants;

public class JQueryModule
{
    public static void contributeContribuableClientInfrastructure(MappedConfiguration<String, AssetPathStack> configuration)
    {
        configuration.add(JQueryClientResourcesConstants.JAVASCRIPT_STACK_JQUERY_DEV, new AssetPathStack("org/got5/tapestry5/tapestry.js",
                "org/got5/tapestry5/jquery/jquery_1_4_2/jquery-1.4.2.js", "org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.core.min.js",
                "org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.position.min.js",
                "org/got5/tapestry5/jquery/ui_1_8/jquery.ui.widget.js", "org/got5/tapestry5/jquery/ui_1_8/minified/jquery.effects.core.min.js",
                "org/got5/tapestry5/jquery/tapestry-jquery.js"));

        configuration.add(JQueryClientResourcesConstants.JAVASCRIPT_STACK_JQUERY, new AssetPathStack("org/got5/tapestry5/tapestry.js",
                "org/got5/tapestry5/jquery/jquery_1_4_2/jquery-1.4.2.min.js", "org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.core.min.js",
                "org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.position.min.js", "org/got5/tapestry5/jquery/ui_1_8/minified/jquery.ui.widget.min.js",
                "org/got5/tapestry5/jquery/ui_1_8/minified/jquery.effects.core.min.js", "org/got5/tapestry5/jquery/tapestry-jquery.js"));

        configuration.add(JQueryClientResourcesConstants.CSS_STACK_JQUERY, new AssetPathStack("${tapestry.default-stylesheet}",
                "org/got5/tapestry5/jquery/themes/ui-lightness/jquery-ui-1.8.custom.css"));
    }

    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration,
            @Inject @Symbol(ClientResourcesConstants.JAVASCRIPT_STACK) String javaScriptStack)
    {
        if (javaScriptStack.startsWith(JQueryClientResourcesConstants.JAVASCRIPT_STACK_JQUERY))
        {
            configuration.addInstance("FormResourcesInclusionWorker", FormResourcesInclusionWorker.class);
            configuration.addInstance("FormFragmentResourcesInclusionWorker", FormFragmentResourcesInclusionWorker.class);
            configuration.addInstance("ZoneResourcesInclusionWorker", ZoneResourcesInclusionWorker.class);
        }

    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration,
            @Inject @Symbol(ClientResourcesConstants.JAVASCRIPT_STACK) String javaScriptStack)
    {
        if (javaScriptStack.startsWith(JQueryClientResourcesConstants.JAVASCRIPT_STACK_JQUERY))
            configuration.add(new LibraryMapping("jquery", "org.got5.tapestry5.jquery"));
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add("tapestry.jquery.path", "classpath:org/got5/tapestry5/jquery");
        configuration.override(ClientResourcesConstants.DISABLED_FORM_AUTOFOCUS, "true");

        configuration.override(ClientResourcesConstants.CSS_STACK, JQueryClientResourcesConstants.CSS_STACK_JQUERY);
    }

}
