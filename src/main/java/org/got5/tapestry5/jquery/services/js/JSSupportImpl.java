package org.got5.tapestry5.jquery.services.js;

import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.slf4j.Logger;

@Scope(ScopeConstants.PERTHREAD)
public class JSSupportImpl implements JSSupport {
	private final JSLocator jsLocator;
	private final StringBuffer scriptBuffer;
	
	public JSSupportImpl(JSLocator jsLocator, Logger logger) {
		this.jsLocator = jsLocator;
		scriptBuffer = new StringBuffer();
	}

	public void addScript(String script) {
		scriptBuffer.append(script);
	}

	public String store() {
		if ( scriptBuffer.length() == 0 ) {
			return null;
		}
		scriptBuffer.insert(0,"function jsOnLoad() {");
		scriptBuffer.append("}");
		return jsLocator.store(scriptBuffer.toString());
	}

}
