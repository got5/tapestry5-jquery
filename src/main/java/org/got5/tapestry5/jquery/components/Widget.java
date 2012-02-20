package org.got5.tapestry5.jquery.components;

import java.util.HashMap;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;

/**
 * @tapestrydoc
 */
@SupportsInformalParameters
public class Widget {

	/*
	 * The name of the JavaScriptStack (plugin) to import
	 * May be a list of stack names, separated by ','
	 */
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String name;

	/*
	 * JavaScript options to pass to the plugin during intialization.
	 * May be a JSONObject or a String when you only have one plugin to insert
	 * Or (if ou have several plugins -> thus several stack names), a HashMap<String,JSONObject) 
	 * where the key is the JavaScriptStack name and the JSONObject is the option to initialize this specific plugin.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private Object options;
	
	/*
	 * Client Id of the element
	 */
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
    @Inject
    private ComponentResources resources;
    
	@Inject
	@Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
	private String jqueryAlias;
	
	private String[] stacks;
	
	private HashMap<String, JSONObject> optionsMap;
	
	@SetupRender
	public void addTheJSLibs(){
		stacks = name.split(",");			
		if(resources.isBound("options")){
			if(options!=""){
				Class optionsType = resources.getBoundType("options");
				if(HashMap.class.equals(optionsType)){
					optionsMap = (HashMap<String, JSONObject>) options;
				}
				else if(JSONObject.class.equals(optionsType)){
					optionsMap = new HashMap<String, JSONObject>();
					optionsMap.put(stacks[0], (JSONObject) options);
				}
				else if(String.class.equals(optionsType)){
					optionsMap = new HashMap<String, JSONObject>();
					optionsMap.put(stacks[0], new JSONObject((String) options));
				}
			}
		}
		else optionsMap = new HashMap<String, JSONObject>();
	}
	
	@BeginRender
    void startDiv(MarkupWriter writer)
    {
        writer.element("div", "id", clientId);
    }
	
	@AfterRender
	void afterRender(MarkupWriter writer) {
		resources.renderInformalParameters(writer);
		writer.end();
		for(String stack : stacks){
			javaScriptSupport.importStack(stack);
			JSONObject params = optionsMap.get(stack);
			if (params==null) params = new JSONObject();
			String init = String.format("%s('#%s').%s(%s);", jqueryAlias, clientId,stack,params);
			javaScriptSupport.addScript(init);
		}
	}
}
