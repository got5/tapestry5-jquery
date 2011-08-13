package org.got5.tapestry5.jquery.services.js;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

public class JSLocatorImpl implements JSLocator {
	private final Map<String, String> scripts = new HashMap<String, String>();
	private final Logger logger;
	private final Request request;
	
	public JSLocatorImpl(Logger logger, Request request) {
		this.logger = logger;
		this.request = request;
	}
	
	public String store(String script) {
		String key = createKey(script);
		scripts.put(key, script);
		return keyToUrl(key);
	}

	public String find(String path) {
		return scripts.get(path);
	}
	
	private String createKey(String script) {
		String key = new String(Hex.encodeHex(DigestUtils.sha(script)));
		return key;
	}
	
	public String keyToUrl(String key) {
		
		return request.getContextPath() + "/js/" + key;
	}

}
