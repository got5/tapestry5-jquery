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
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.compatibility.Compatibility;
import org.apache.tapestry5.services.compatibility.Trait;
import org.apache.tapestry5.services.javascript.JavaScriptModuleConfiguration;
import org.apache.tapestry5.services.javascript.ModuleManager;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.got5.tapestry5.jquery.EffectsConstants;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.got5.tapestry5.jquery.services.impl.EffectsParamImpl;
import org.got5.tapestry5.jquery.services.impl.JGrowlManagerImpl;
import org.got5.tapestry5.jquery.services.impl.JavaScriptFilesConfigurationImpl;
import org.got5.tapestry5.jquery.services.impl.RenderTrackerImpl;
import org.got5.tapestry5.jquery.services.impl.WidgetParamsImpl;
import org.got5.tapestry5.jquery.services.js.JSModule;
import org.got5.tapestry5.jquery.services.messages.MessageProvider;
import org.got5.tapestry5.jquery.services.messages.MessageProviderImpl;

@SubModule(JSModule.class)
public class JQueryModule {

	public static void contributeComponentClassResolver(
			Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("jquery",
				"org.got5.tapestry5.jquery"));
	}

	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void contributeFactoryDefaults(
			MappedConfiguration<String, Object> configuration) {

		configuration.add(JQuerySymbolConstants.JQUERY_VERSION, "1.12.2");
		configuration.add(JQuerySymbolConstants.JQUERY_UI_VERSION, "1.12.1");
		configuration.add(JQuerySymbolConstants.JQUERY_JSON_VERSION, "2.4");

		configuration.add(JQuerySymbolConstants.ASSETS_ROOT,
				"classpath:/META-INF/modules/tjq");
		configuration.add(JQuerySymbolConstants.JQUERY_UI_PATH,
				"${jquery.assets.root}/vendor/ui");
		configuration.add(JQuerySymbolConstants.ASSETS_PATH,
				"${jquery.assets.root}/lib");

		configuration.add(JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME,
				"${jquery.ui.path}/themes/smoothness/jquery-ui.css");

		configuration.add(JQuerySymbolConstants.ADD_MOUSEWHEEL_EVENT, false);

		configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, true);

		configuration.add(JQuerySymbolConstants.JQUERY_CORE_PATH,
				"${jquery.assets.root}/vendor/jquery.js");

		// MIGRATION TO 5.4
		configuration.add(JQuerySymbolConstants.TAPESTRY_JQUERY_PATH,
				"classpath:org/got5/tapestry5/jquery");
		configuration.add(JQuerySymbolConstants.TAPESTRY_JS_PATH,
				"classpath:org/got5/tapestry5/tapestry.js");

		configuration.add(JQuerySymbolConstants.JQUERY_VALIDATE_PATH,
				"classpath:org/got5/tapestry5/jquery/validate/1_7");

		configuration.add(JQuerySymbolConstants.JQUERY_ALIAS, "$");

		configuration.add(JQuerySymbolConstants.PARAMETER_PREFIX, "tjq-");
		configuration.add(JQuerySymbolConstants.USE_MINIFIED_JS,
				SymbolConstants.PRODUCTION_MODE_VALUE);

	}

	@Contribute(Compatibility.class)
	public static void contributeCompatibility(
			MappedConfiguration<Trait, Object> configuration,
			@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE) Boolean prototype) {

		if (prototype)
			configuration.add(Trait.SCRIPTACULOUS, false);
	}

	public static void contributeClasspathAssetAliasManager(
			MappedConfiguration<String, String> configuration) {
		configuration.add("tap-jquery", "org/got5/tapestry5");
		configuration.add("tapestry-jquery", "META-INF/modules/tjq");
	}

	public static void contributeBindingSource(
			MappedConfiguration<String, BindingFactory> configuration,
			@InjectService("SelectorBindingFactory") BindingFactory selectorBindingFactory) {
		configuration.add("selector", selectorBindingFactory);

	}

	public static void bind(ServiceBinder binder) {
		binder.bind(WidgetParams.class, WidgetParamsImpl.class);
		binder.bind(EffectsParam.class, EffectsParamImpl.class);
		binder.bind(BindingFactory.class, SelectorBindingFactory.class).withId(
				"SelectorBindingFactory");
		binder.bind(RenderTracker.class, RenderTrackerImpl.class);
		binder.bind(JavaScriptFilesConfiguration.class,
				JavaScriptFilesConfigurationImpl.class);
		binder.bind(MessageProvider.class, MessageProviderImpl.class);
		binder.bind(JGrowlManager.class, JGrowlManagerImpl.class);
	}

	@Contribute(EffectsParam.class)
	public void addEffectsFile(Configuration<String> configuration) {
	    /*
	     * By Default, we import the JavaScript file of the HighLight Effect.
	     */
		configuration.add(EffectsConstants.HIGHLIGHT);
	}

	@Contribute(ComponentClassTransformWorker2.class)
	@Primary
	public static void addWorker(
			OrderedConfiguration<ComponentClassTransformWorker2> configuration,
			@Symbol(JQuerySymbolConstants.SUPPRESS_PROTOTYPE) boolean suppressPrototype) {

		// if (suppressPrototype) {
		// configuration.addInstance("FormResourcesInclusionWorker",
		// FormResourcesInclusionWorker.class, "after:RenderPhase");
		// }
		configuration.addInstance("RenderTrackerMixinWorker",
				RenderTrackerMixinWorker.class);

		// note: the ordering must ensure that the worker gets added after the
		// RenderPhase-Worker!
		configuration.addInstance("ImportJQueryUIWorker",
				ImportJQueryUIWorker.class, "before:Import",
				"after:RenderPhase");
	}


	@Contribute(ModuleManager.class)
	public static void setupComponentsShims(
			MappedConfiguration<String, Object> configuration,
			@Inject @Path("/META-INF/modules/tjq/datefield.js") Resource datefield,
			@Inject @Path("${jquery.assets.root}/vendor/jquery.mousewheel.js") Resource jquerymousewheel,
			@Symbol(JQuerySymbolConstants.ADD_MOUSEWHEEL_EVENT) boolean mouseWheelIncluded) {

		configuration.add("t5/core/datefield",
				new JavaScriptModuleConfiguration(datefield));

		if (mouseWheelIncluded)
			configuration.add("vendor/jquerymousewheel",
					new JavaScriptModuleConfiguration(jquerymousewheel)
							.dependsOn("jquery"));
	}

	public static void contributeComponentMessagesSource(
			@Value("/org/got5/tapestry5/JQueryCatalog.properties") Resource jQueryCatalog,
			OrderedConfiguration<Resource> configuration) {

		// Catalog used to store messages from mixins
		configuration.add("JQueryCatalog", jQueryCatalog);
	}
}
