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
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.got5.tapestry5.jquery.services.javascript.AjaxUploadStack;
import org.got5.tapestry5.jquery.services.javascript.FormFragmentSupportStack;
import org.got5.tapestry5.jquery.services.javascript.FormSupportStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryDateFieldStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryJavaScriptStack;
import org.got5.tapestry5.jquery.services.javascript.ZoneSupportStack;

public class JQueryModule
{
    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration)
    {
        configuration.overrideInstance(InternalConstants.CORE_STACK_NAME, JQueryJavaScriptStack.class);
        configuration.overrideInstance("core-datefield", JQueryDateFieldStack.class);
        configuration.addInstance(FormSupportStack.STACK_ID, FormSupportStack.class);
        configuration.addInstance(FormFragmentSupportStack.STACK_ID, FormFragmentSupportStack.class);
        configuration.addInstance(ZoneSupportStack.STACK_ID, ZoneSupportStack.class);
        configuration.addInstance(AjaxUploadStack.STACK_ID, AjaxUploadStack.class);
    }

    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration)
    {
        configuration.addInstance("FormResourcesInclusionWorker", FormResourcesInclusionWorker.class);
        configuration.addInstance("FormFragmentResourcesInclusionWorker", FormFragmentResourcesInclusionWorker.class);
        configuration.addInstance("ZoneResourcesInclusionWorker", ZoneResourcesInclusionWorker.class);
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("jquery", "org.got5.tapestry5.jquery"));
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add("tapestry.jquery.path", "classpath:org/got5/tapestry5/jquery");
    }

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("tap-jquery", "org/got5/tapestry5");
    }



}
