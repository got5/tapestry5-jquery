package org.got5.tapestry5.jquery.services.js;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.commons.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.http.services.Dispatcher;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.PartialMarkupRenderer;
import org.apache.tapestry5.services.PartialMarkupRendererFilter;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;

public class JSModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(Dispatcher.class,JSDispatcher.class).withId("js");
		binder.bind(JSHandler.class, JSHanderImpl.class);
		binder.bind(JSLocator.class,JSLocatorSession.class);
		binder.bind(JSSupport.class,JSSupportImpl.class);
	}
	
	public static void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
            @InjectService("js") Dispatcher js) {
				configuration.add("js", js, "before:Asset");

	}
	
	  public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration, 
	    		final Logger logger, final Environment environment, final RequestGlobals requestGlobals, final JSSupport jsSupport) {
	    	 MarkupRendererFilter documentLinker = new MarkupRendererFilter()
	    	 	        {
	    	 	            public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer)
	    	 	            {
	    	 	            	JavaScriptSupport javaScriptSupport = environment.peekRequired(JavaScriptSupport.class);	    	 	
	    	 	                renderer.renderMarkup(writer);
	    	 	               String url = jsSupport.store();
								if ( url != null ) {
									javaScriptSupport.importJavaScriptLibrary(url);
									javaScriptSupport.addScript("jsOnLoad();");
								}	    	 	                
	    	 	            }
	    	 	        };
	    	 	       configuration.add("JSLoader", documentLinker, "after:JavaScriptSupport");
	    }
	
	 public void contributePartialMarkupRenderer(OrderedConfiguration<PartialMarkupRendererFilter> configuration, 
	    		final Logger logger, final Environment environment, final RequestGlobals requestGlobals, final JSSupport jsSupport) {
	    	PartialMarkupRendererFilter documentLinker = new PartialMarkupRendererFilter()
	    	 	        {

							public void renderMarkup(MarkupWriter writer, JSONObject arg1, PartialMarkupRenderer renderer) {
								JavaScriptSupport javaScriptSupport = environment.peekRequired(JavaScriptSupport.class);
								renderer.renderMarkup(writer, arg1);
								String url = jsSupport.store();
								if ( url != null ) {
									javaScriptSupport.importJavaScriptLibrary(url);
									javaScriptSupport.addScript("jsOnLoad();");
								}
							}
	    	 	        };
	    	 	       configuration.add("JSLoader", documentLinker,"after:JavaScriptSupport");
	    }

}
