package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;

/**
 * @author Emmanuel DEMEY
 * Service used by the jQueryJavaScriptStack for retrieving the jQuery version of a Prototype/Scriptaculous JavaScript file. 
 */
@UsesMappedConfiguration(value = String.class)
public interface JavaScriptFilesConfiguration {
	public abstract Asset getAsset(Asset original);
}
