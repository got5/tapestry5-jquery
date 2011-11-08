package org.got5.tapestry5.jquery.services.impl;

import org.got5.tapestry5.jquery.services.JQueryUITheme;


public class JQueryUIThemeImpl implements JQueryUITheme {
	
	private String path;
	
	public void changePath(String path){ this.path = path; }
	
	public String getPath() { return this.path; }
	
}
