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
		if(javaScriptFilesConfiguration.containsKey(original.getResource().getFile())) {
			String assetPath = javaScriptFilesConfiguration.get(original.getResource().getFile());
			return InternalUtils.isBlank(assetPath) ? null : this.as.getExpandedAsset(assetPath);
		}
		return original;
	}
}
