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

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.internal.services.javascript.CoreJavaScriptStack;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.ClasspathProvider;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.got5.tapestry5.jquery.EffectsConstants;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.impl.EffectsParamImpl;
import org.got5.tapestry5.jquery.services.impl.JavaScriptFilesConfigurationImpl;
import org.got5.tapestry5.jquery.services.impl.RenderTrackerImpl;
import org.got5.tapestry5.jquery.services.impl.WidgetParamsImpl;
import org.got5.tapestry5.jquery.services.javascript.AjaxUploadStack;
import org.got5.tapestry5.jquery.services.javascript.ConfirmStack;
import org.got5.tapestry5.jquery.services.javascript.ContextMenuStack;
import org.got5.tapestry5.jquery.services.javascript.DDSlickStack;
import org.got5.tapestry5.jquery.services.javascript.DataTableStack;
import org.got5.tapestry5.jquery.services.javascript.FlexSliderStack;
import org.got5.tapestry5.jquery.services.javascript.FormFragmentSupportStack;
import org.got5.tapestry5.jquery.services.javascript.FormSupportStack;
import org.got5.tapestry5.jquery.services.javascript.GalleryStack;
import org.got5.tapestry5.jquery.services.javascript.InPlaceEditorStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryDateFieldStack;
import org.got5.tapestry5.jquery.services.javascript.JQueryJavaScriptStack;
import org.got5.tapestry5.jquery.services.javascript.JScrollPaneStack;
import org.got5.tapestry5.jquery.services.javascript.PlaceholderStack;
import org.got5.tapestry5.jquery.services.javascript.SuperfishStack;
import org.got5.tapestry5.jquery.services.javascript.widgets.Slider;
import org.got5.tapestry5.jquery.services.js.JSModule;
import org.got5.tapestry5.jquery.services.messages.MessageProvider;
import org.got5.tapestry5.jquery.services.messages.MessageProviderImpl;

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

        
        /*
         * JavaScriptStack for Components/Mixins
         */
        configuration.addInstance(Slider.STACK_ID, Slider.class);
        configuration.addInstance(SuperfishStack.STACK_ID, SuperfishStack.class);
        configuration.addInstance(AjaxUploadStack.STACK_ID, AjaxUploadStack.class);
        configuration.addInstance(GalleryStack.STACK_ID, GalleryStack.class);
        configuration.addInstance(DataTableStack.STACK_ID, DataTableStack.class);
        configuration.addInstance(InPlaceEditorStack.STACK_ID, InPlaceEditorStack.class);
        configuration.addInstance(PlaceholderStack.STACK_ID, PlaceholderStack.class);
        configuration.addInstance(DDSlickStack.STACK_ID, DDSlickStack.class);
        configuration.addInstance(JScrollPaneStack.STACK_ID, JScrollPaneStack.class);
		configuration.addInstance(ConfirmStack.STACK_ID, ConfirmStack.class);
		configuration.addInstance(ContextMenuStack.STACK_ID, ContextMenuStack.class);
        configuration.addInstance(FlexSliderStack.STACK_ID, FlexSliderStack.class);
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("jquery", "org.got5.tapestry5.jquery"));
    }

    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(JQuerySymbolConstants.TAPESTRY_JQUERY_PATH, "classpath:org/got5/tapestry5/jquery");
        configuration.add(JQuerySymbolConstants.TAPESTRY_JS_PATH, "classpath:org/got5/tapestry5/tapestry.js");


        configuration.add(JQuerySymbolConstants.JQUERY_CORE_PATH, "classpath:org/got5/tapestry5/jquery/jquery_core/jquery-1.7.2.js");
        configuration.add(JQuerySymbolConstants.JQUERY_VERSION, "1.7.2");


        configuration.add(JQuerySymbolConstants.JQUERY_UI_PATH, "classpath:org/got5/tapestry5/jquery/ui_1_8_24");
        configuration.add(JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME, "classpath:org/got5/tapestry5/jquery/themes/ui-lightness/jquery-ui.css");

        configuration.add(JQuerySymbolConstants.JQUERY_VALIDATE_PATH, "classpath:org/got5/tapestry5/jquery/validate/1_7");
        configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, true);
        configuration.add(JQuerySymbolConstants.JQUERY_ALIAS, "$");

        configuration.add(JQuerySymbolConstants.ASSETS_PATH, "classpath:org/got5/tapestry5/jquery/assets");
        configuration.add(JQuerySymbolConstants.PARAMETER_PREFIX, "tjq-");
        configuration.add(JQuerySymbolConstants.USE_MINIFIED_JS, SymbolConstants.PRODUCTION_MODE_VALUE);
        
        configuration.add(JQuerySymbolConstants.ADD_MOUSEWHEEL_EVENT, false);
        configuration.add(JQuerySymbolConstants.INCLUDE_DATEPICKER_I18N, true);

        configuration.add(JQuerySymbolConstants.EXCLUDE_CORE_JS_STACK, false);
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
      binder.bind(AjaxUploadDecoder.class, AjaxUploadDecoderImpl.class).scope(ScopeConstants.PERTHREAD);
      binder.bind(JavaScriptFilesConfiguration.class, JavaScriptFilesConfigurationImpl.class);
	  binder.bind(MessageProvider.class, MessageProviderImpl.class);
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
    
    @Contribute(JavaScriptFilesConfiguration.class)
    public void addJavaScriptFilesRules(MappedConfiguration<String, String> configuration){

    	configuration.add("t5-prototype.js", "${tapestry.jquery.path}/t5-jquery.js");
    	configuration.add("tapestry.js", "${tapestry.jquery.path}/tapestry-jquery.js");
    	configuration.add("t5-console.js", "${tapestry.jquery.path}/t5-console-jquery.js");
    	configuration.add("t5-dom.js", "${tapestry.jquery.path}/t5-dom-jquery.js");
    	configuration.add("t5-alerts.js", "${tapestry.jquery.path}/t5-alerts-jquery.js");
    	configuration.add("t5-ajax.js", "${tapestry.jquery.path}/t5-ajax-jquery.js");
    	configuration.add("tree.js", "${tapestry.jquery.path}/t5-tree-jquery.js");
    	configuration.add("prototype.js", "");
    	configuration.add("scriptaculous.js", "");
    	configuration.add("effects.js", "");
    	configuration.add("exceptiondisplay.js", "");
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
    @ClasspathProvider
    public static void modifyJsfile(MethodAdviceReceiver receiver, final AssetSource source,
    		@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE) boolean prototype)
    	throws SecurityException, NoSuchMethodException{

    	MethodAdvice advise = new MethodAdvice() {

			public void advise(MethodInvocation invocation) {

				Resource res = (Resource) invocation.getParameter(0);
				if(res.getPath().contains("ProgressiveDisplay.js")){
					invocation.setParameter(0, source.getExpandedAsset("${tapestry.jquery.path}/assets/components/progressiveDisplay/progressiveDisplay-jquery.js").getResource());
				}
				else if(res.getPath().contains("exceptiondisplay.js")){
					invocation.setParameter(0, source.getExpandedAsset("${tapestry.jquery.path}/exceptiondisplay-jquery.js").getResource());
				}
				else if(res.getPath().contains("tapestry-beanvalidator.js")){
					invocation.setParameter(0, source.getExpandedAsset("${tapestry.jquery.path}/tapestry-beanvalidator-jquery.js").getResource());
				}
				invocation.proceed();
			}
		};

		if(prototype)
			receiver.adviseMethod(receiver.getInterface().getMethod("createAsset", Resource.class),advise);
    }
	
	public static void contributeComponentMessagesSource (
			@Value("/org/got5/tapestry5/JQueryCatalog.properties") Resource jQueryCatalog,
			OrderedConfiguration<Resource> configuration) {
		//Catalog used to store messages from mixins
		configuration.add("JQueryCatalog", jQueryCatalog);
	}
}
