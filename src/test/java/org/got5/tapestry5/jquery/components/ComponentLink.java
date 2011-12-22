package org.got5.tapestry5.jquery.components;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;

public class ComponentLink {

	@Parameter(required=true, allowNull=false, defaultPrefix=BindingConstants.LITERAL)
	private String url;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String description;
	
	
	@SetupRender
	public void init(MarkupWriter w){
		w.element("a","href",url);
		w.write(url);
		w.end();
		if(StringUtils.isNotEmpty(description)){
			w.write(" "+description);
		}
		
	}
	
		
	
	
}
