package org.got5.tapestry5.jquery.services.js;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.Dispatcher;

public class JSModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(Dispatcher.class,JSDispatcher.class).withId("js");
		binder.bind(JSHandler.class, JSHanderImpl.class);
		binder.bind(JSLocator.class,JSLocatorImpl.class);
		binder.bind(JSSupport.class,JSSupportImpl.class);
	}
	
	public static void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
            @InjectService("js") Dispatcher js) {
				configuration.add("js", js, "after:Asset");

	}

}
