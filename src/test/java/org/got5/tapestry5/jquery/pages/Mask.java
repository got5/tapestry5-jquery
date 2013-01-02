package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class Mask {

	@Property
	private String value;
	
	@Inject private JavaScriptSupport js;
	
	public void afterRender(){
		js.addScript(InitializationPriority.LATE, "keyProblem()");
	}
}
