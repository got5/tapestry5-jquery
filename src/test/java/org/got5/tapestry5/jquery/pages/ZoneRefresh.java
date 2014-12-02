package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;

public class ZoneRefresh {

	@Property
	@Persist
	private int i;

	@Inject
	private JavaScriptSupport js;

	@OnEvent(JQueryEventConstants.REFRESH)
	public void refesh(){
		i++;
	}

	/**
	 * Add handlers method to the links, in order to stop/start refreshing the zone.
	 */
	public void afterRender(){
		js.addScript("$('#stop').on('click', function(){$('#clickZone').trigger('stopRefresh');});");
		js.addScript("$('#start').on('click', function(){$('#clickZone').trigger('startRefresh');});");
	}
}