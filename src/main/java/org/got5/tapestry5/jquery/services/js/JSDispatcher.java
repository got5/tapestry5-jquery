package org.got5.tapestry5.jquery.services.js;

import java.io.IOException;

import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

public class JSDispatcher implements Dispatcher {
	
	private final String pathPrefix = "/js";
	private final JSHandler jsHandler;
	private final Logger logger;
	
	public JSDispatcher(JSHandler jsHander, Logger logger) {
		this.jsHandler = jsHander;
		this.logger = logger;
	}

	public boolean dispatch(Request request, Response response) throws IOException {
		String path = request.getPath();
        if (!path.startsWith(pathPrefix))
                return false;
        

        String virtualPath = path.substring(pathPrefix.length()+1);
        return jsHandler.handleRequest(virtualPath,response);
	}

}
