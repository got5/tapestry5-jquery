package org.got5.tapestry5.jquery.services.js;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.tapestry5.http.services.Response;
import org.slf4j.Logger;

public class JSHanderImpl implements JSHandler {
	private final JSLocator jsLocator;
	private final Logger logger;
	
	public JSHanderImpl(JSLocator jsLocator, Logger logger) {
		this.jsLocator = jsLocator;
		this.logger = logger;
	}

	public boolean handleRequest(String path, Response response) throws IOException {
		return sendResponse(jsLocator.find(path), response);
	}
	
	 boolean sendResponse(String payload, Response response) throws IOException {
		 
         if (payload == null)
                 return false;
                 
         PrintWriter pw = response.getPrintWriter("application/javascript; charset=utf-8");
         pw.print(payload);
         pw.flush();
         pw.close();
         return true;
	 }

}
