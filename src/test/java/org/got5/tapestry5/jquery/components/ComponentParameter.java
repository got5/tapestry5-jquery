package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;

public class ComponentParameter {

	@Parameter(required=true, allowNull=false, defaultPrefix=BindingConstants.LITERAL)
	private String name;
	
	@Parameter(required=true, allowNull=false, defaultPrefix=BindingConstants.LITERAL)
	private String required;
	
	@Parameter(required=true, allowNull=false, defaultPrefix=BindingConstants.LITERAL)
	private String javaType;
	
	@Parameter(required=false, defaultPrefix=BindingConstants.LITERAL)
	private String defaultValue;
	
	@Parameter(required=true, allowNull=false, defaultPrefix=BindingConstants.LITERAL)
	private String prefix;
	
	@Parameter(required=false, defaultPrefix=BindingConstants.LITERAL)
	private String description;
	
	
	
	@SetupRender
	public void init(MarkupWriter w){
		
	
		w.element("td", "class", "name");
		w.write(name);
		w.end();
		w.element("td", "class", "center");
		w.write(required);
		w.end();
		w.element("td", "class", "center");
		w.write(javaType);
		w.end();
		w.element("td", "class", "center");
		w.write(prefix);
		w.end();
		w.element("td", "class", "center");
		w.write(defaultValue);
		w.end();
		w.element("td","class","description");
		w.write(description);
		w.end();
		
		
	}
	
		
	
	
}
