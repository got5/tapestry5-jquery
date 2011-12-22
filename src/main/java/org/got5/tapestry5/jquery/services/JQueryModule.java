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
import org.apache.tapestry5.internal.services.javascript.CoreJavaScriptStack;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.got5.tapestry5.jquery.EffectsConstants;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.impl.EffectsParamImpl;
import org.got5.tapestry5.jquery.services.impl.RenderTrackerImpl;
import org.got5.tapestry5.jquery.services.impl.WidgetParamsImpl;
import org.got5.tapestry5.jquery.services.javascript.AjaxUploadStack;
import org.got5.tapestry5.jquery.services.javascript.FormFragmentSupportStack;
import org.got5.tapestry5.jquery.services.javascript.FormSupportStack;
import org.got5.tapestry5.jquery.services.javascript.GalleryStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryDateFieldStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryJavaScriptStack;
import org.got5.tapestry5.jquery.services.javascript.widgets.Slider;
import org.got5.tapestry5.jquery.services.js.JSModule;

@SubModule(JSModule.class)
public class JQueryModule
{
    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration,
    		@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE)
            boolean suppressPrototype)
    {
    	configuration.addInstance(JQuerySymbolConstants.PROTOTYPE_STACK, CoreJavaScriptStack.class);
    	configuration.overrideInstance(InternalConstants.CORE_STACK_NAME, JQueryJavaScriptStack.class);
    	if(suppressPrototype)
    	{
    		configuration.overrideInstance("core-datefield", JQueryDateFieldStack.class);
    		configuration.addInstance(FormSupportStack.STACK_ID, FormSupportStack.class);
    		configuration.addInstance(FormFragmentSupportStack.STACK_ID, FormFragmentSupportStack.class);
    	}
    	configuration.addInstance(AjaxUploadStack.STACK_ID, AjaxUploadStack.class);
        configuration.addInstance(GalleryStack.STACK_ID, GalleryStack.class);
        
        configuration.addInstance("slider", Slider.class);
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("jquery", "org.got5.tapestry5.jquery"));
    }

    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(JQuerySymbolConstants.TAPESTRY_JQUERY_PATH, "classpath:org/got5/tapestry5/jquery");
        configuration.add(JQuerySymbolConstants.TAPESTRY_JS_PATH, "classpath:org/got5/tapestry5/tapestry.js");

        configuration.add(JQuerySymbolConstants.JQUERY_CORE_PATH, "classpath:org/got5/tapestry5/jquery/jquery_core/jquery-1.7.1.js");
        configuration.add(JQuerySymbolConstants.JQUERY_VERSION, "1.7.1");

        configuration.add(JQuerySymbolConstants.JQUERY_UI_PATH, "classpath:org/got5/tapestry5/jquery/ui_1_8");
        configuration.add(JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME, "classpath:org/got5/tapestry5/jquery/themes/ui-lightness/jquery-ui-1.8.15.custom.css");

        configuration.add(JQuerySymbolConstants.JQUERY_VALIDATE_PATH, "classpath:org/got5/tapestry5/jquery/validate/1_7");
        configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "true");
        configuration.add(JQuerySymbolConstants.JQUERY_ALIAS, "$");

        configuration.add(JQuerySymbolConstants.ASSETS_PATH, "classpath:org/got5/tapestry5/jquery/assets");

    }

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("tap-jquery", "org/got5/tapestry5");
    }

    public static void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration,
    		@InjectService("SelectorBindingFactory")
    		BindingFactory selectorBindingFactory)
    {
        configuration.add("selector", selectorBindingFactory);

    }

    public static void bind(ServiceBinder binder)
    {
      binder.bind(WidgetParams.class, WidgetParamsImpl.class);
      binder.bind(EffectsParam.class, EffectsParamImpl.class);
      binder.bind(BindingFactory.class,SelectorBindingFactory.class).withId("SelectorBindingFactory");
      binder.bind(RenderTracker.class, RenderTrackerImpl.class);
      binder.bind(AjaxUploadDecoder.class, AjaxUploadDecoderImpl.class);
    }


    /**
     * By Default, we import the JavaScript file of the HighLight Effect.
     * @param configuration
     */
    @Contribute(EffectsParam.class)
    public void addEffectsFile(Configuration<String> configuration){
    	configuration.add(EffectsConstants.HIGHLIGHT);
    	configuration.add(EffectsConstants.SHOW);
    }

    @Contribute(ComponentClassTransformWorker2.class)
    @Primary
    public static void  addWorker(OrderedConfiguration<ComponentClassTransformWorker2> configuration,
    		@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE) boolean suppressPrototype) {

    	if(suppressPrototype)
    	{
    		configuration.addInstance("FormFragmentResourcesInclusionWorker", FormFragmentResourcesInclusionWorker.class, "after:RenderPhase");
    		configuration.addInstance("FormResourcesInclusionWorker", FormResourcesInclusionWorker.class, "after:RenderPhase");
    	}
    	configuration.addInstance("RenderTrackerMixinWorker", RenderTrackerMixinWorker.class);

    	// note: the ordering must ensure that the worker gets added after the RenderPhase-Worker!
    	configuration.addInstance("DateFieldWorker", DateFieldWorker.class, "after:RenderPhase");
    	configuration.addInstance("ImportJQueryUIWorker", ImportJQueryUIWorker.class, "before:Import", "after:RenderPhase");
    }

    public static void contributeHttpServletRequestHandler(final OrderedConfiguration<HttpServletRequestFilter> configuration,
                                                           final AjaxUploadDecoder ajaxUploadDecoder) {

       configuration.add("AjaxUploadFilter", new AjaxUploadServletRequestFilter(ajaxUploadDecoder), "after:IgnoredPaths");
    }

    @Advise
    @Match("AssetPathConverter")
    public static void modifyJsfile(MethodAdviceReceiver receiver, final AssetSource source)
    	throws SecurityException, NoSuchMethodException{

    	MethodAdvice advise = new MethodAdvice() {

			public void advise(MethodInvocation invocation) {

				invocation.proceed();

				if(invocation.getReturnValue().toString().endsWith("exceptiondisplay.js")){

					invocation.setReturnValue( source.getExpandedAsset("${tapestry.jquery.path}/exceptiondisplay-jquery.js").toClientURL());

				}

			}
		};
		receiver.adviseMethod(receiver.getInterface().getMethod("convertAssetPath", String.class),advise);
    }

}
