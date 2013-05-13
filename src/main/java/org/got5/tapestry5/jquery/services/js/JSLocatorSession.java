package org.got5.tapestry5.jquery.services.js;

import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

/*
 * Locator that just uses the session. Simple enough and should work anywhere.
 */
public class JSLocatorSession implements JSLocator {
	private final String prefix = "JS";
	private final Request request;
	
	public JSLocatorSession(Request request) {
		this.request = request;		
	}

	public String store(final String script) {
		Session session = request.getSession(true);	
		String key = createKey(script);
		Integer max = 0;
		if ( session.getAttribute(key) != null ) {
			List<String> names = session.getAttributeNames(prefix);
			for ( String name : names ) {
				String[] parts = name.split("-");
				Integer seq = new Integer(parts[1]);
				if ( seq > max ) {
					max = seq;
				}
			}
		}
		final String fkey = key + "-" + (max+1);
		session.setAttribute(fkey, script);
		return keyToUrl(fkey);
	}

	public String find(String path) {
		
		Session session = request.getSession(false);
		
		if(session != null){
			String script = (String) session.getAttribute(path);
			session.setAttribute(path, null);  
			return script;
		}
		
		return null;
	}
	
	private String createKey(String script) {
		return prefix + new String(Hex.encodeHex(DigestUtils.sha(script)));
	}
	
	public String keyToUrl(String key) {		
		return String.format("%s/js/%s",request.getContextPath(), key);
	}

}
