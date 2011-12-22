package org.got5.tapestry5.jquery.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

@SupportsInformalParameters
public class ComponentDocumentation {

	private static final String LINK_PREFIX = "link_";
	private static final String PARAM_PREFIX = "param_";

	@Inject
	private ComponentResources resources;
	
	private List<String> parameters;
	
	@Property
	@Parameter(required=true, allowNull=false)
	private Block title;
	
	@Property
	@Parameter(required=true, allowNull=false)
	private Block description;
	
	@Property
	private List<String> links;
	
	@Property
	private List<String> params;
	
	@Property
	private String currentLink;
	
	@Property
	private String currentParam;
	
	@Property
	private int index;
	
	@SetupRender
	public void init(){
		parameters = resources.getInformalParameterNames();
		links = new ArrayList<String>();
		params = new ArrayList<String>();
		for (String param : parameters) {
			if(param.startsWith(LINK_PREFIX)){
				links.add(param);
			}
			if(param.startsWith(PARAM_PREFIX)){
				params.add(param);
			}
		}
	}
	
	public boolean getHasLinks(){
		return links.size()>0;
	}
	
	public boolean getHasParameters(){
		return params.size()>0;
	}
	
	public Block getCurrentLinkBlock(){
		return resources.getInformalParameter(currentLink, Block.class);
	}
	
	public Block getCurrentParamBlock(){
		return resources.getInformalParameter(currentParam, Block.class);
	}
	
	public String getRowClass(){
		if(index%2==0){
			return "even";
		}else{
			return "odd";
		}
	}
	
	
	
	
	
}
