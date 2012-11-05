package org.got5.tapestry5.jquery.services.impl;

import java.util.Map;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.AssetSource;
import org.got5.tapestry5.jquery.services.JavaScriptFilesConfiguration;

public class JavaScriptFilesConfigurationImpl implements
		JavaScriptFilesConfiguration {

	private Map<String, String> javaScriptFilesConfiguration;
	private AssetSource as;
	
	public JavaScriptFilesConfigurationImpl(Map<String, String> javaScriptFilesConfiguration, AssetSource as) {
		super();
		this.javaScriptFilesConfiguration = javaScriptFilesConfiguration;
		this.as = as;
	}

	public Asset getAsset(Asset original) {
		for(String key : javaScriptFilesConfiguration.keySet()){
			if(original.getResource().getFile().endsWith(key)){
				return InternalUtils.isBlank(javaScriptFilesConfiguration.get(key)) ? null : this.as.getExpandedAsset(javaScriptFilesConfiguration.get(key));
			}
		}
		return original;
	}
}
