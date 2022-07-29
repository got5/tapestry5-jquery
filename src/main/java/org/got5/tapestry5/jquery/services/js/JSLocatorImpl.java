package org.got5.tapestry5.jquery.services.js;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.http.services.Session;
import org.slf4j.Logger;

/*
 * Simple cache. Should be OK for single webservers
 * with high script reuse
 * 
 * TODO
 * create 3 maps
 * newScripts
 * reusedScripts
 * removedScripts
 * 
 * This will allow better management and remove the race condition
 * 
 * Would it be better to store this in the Session?
 */
public class JSLocatorImpl implements JSLocator {
	private final Map<String, Content> scripts = new HashMap<String, Content>(1000);
	private final Logger logger;
	private final Request request;
	
	public JSLocatorImpl(Logger logger, Request request) {
		this.logger = logger;
		this.request = request;
		Session session = request.getSession(true);
	}
	
	public String store(final String script) {
		String key = createKey(script);
		Content content = scripts.get(key);
		if ( content != null ) {
			content.reuse();
			return keyToUrl(key);
		}
		scripts.put(key, new Content(script));
		// only flush if we are making the cache too big
		if ( scripts.size() > 900 ) {
			flush();
		}
		return keyToUrl(key);
	}

	public String find(String path) {
		return scripts.get(path).getScript();
	}
	
	private synchronized void flush() {
		long time = new Date().getTime() - 1000*60*5; //5 minutes
		List<String> remove = new ArrayList<String>(scripts.size());
		for ( Entry<String, Content> entry : scripts.entrySet()) {
			if ( entry.getValue().isExpired(time) ) {
				remove.add(entry.getKey());			
			}		
		}
		logger.info("size {} remove {}",scripts.size(),remove.size());
		for ( String key : remove ) {
			Content content = scripts.get(key);
			// keep them if reused. There is is still a race here but it's a short one
			// could put them in a removed map so they hang around a bit longer
			// then just clear that map
			if ( content.isExpired(time)) {
				scripts.remove(key);
			}
		}
	}
	
	private String createKey(String script) {
		return new String(Hex.encodeHex(DigestUtils.sha(script)));
	}
	
	public String keyToUrl(String key) {		
		return String.format("%s/js/%s",request.getContextPath(), key);
	}
	
	class Content {
		private long date;
		private final String script;
		private boolean reused = false;
		
		Content(String script) {
			this.script = script;
			date = new Date().getTime();
		}
		// hang on to static ones
		boolean isExpired(long time) {
			if ( reused && scripts.size() < 990) {
				return false;
			}
			return date < time;
		}
		
		String getScript() {
			return script;
		}
		
		void reuse() {
			date = new Date().getTime();
			reused = true;
		}
	}

}
