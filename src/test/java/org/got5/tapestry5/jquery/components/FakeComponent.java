package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

@SupportsInformalParameters
public class FakeComponent {
	
	@Inject
	private ComponentResources resources;
	
	public String getJsonParameter(){
		return JQueryUtils.convertInformalParametersToJson(resources, "tjq").toString();
	}
}
