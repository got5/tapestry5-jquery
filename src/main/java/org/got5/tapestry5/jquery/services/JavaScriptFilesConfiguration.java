package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;

@UsesMappedConfiguration(value = String.class)
public interface JavaScriptFilesConfiguration {
	public abstract Asset getAsset(Asset original);
}
