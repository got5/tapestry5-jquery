//
// Copyright 2010 GOT5 (GO Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
* @since 2.1.1
* @see http://sorgalla.com/jcarousel/
*/
@Import(library = {"${assets.path}/components/carousel/jquery.jcarousel.js","${assets.path}/components/carousel/carousel.js"},stylesheet={"context:css/tango/skin.css"})

@SupportsInformalParameters
public class Carousel implements ClientElement {
	 
	
	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;
	
	@Parameter
	private JSONObject params;
	
	@Parameter(value="jcarousel-skin-tango", defaultPrefix=BindingConstants.LITERAL)
	private String className;
	
	@Inject
	private ComponentResources componentResources;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Property
	private String currentParameterName;
	
	@SetupRender
	public void init(MarkupWriter w){
		w.element("ul","id",clientId,"class",className);
		componentResources.renderInformalParameters(w);
	}
	
	
	@AfterRender()
	public void finish(MarkupWriter w){
		w.end();
		JSONObject jso = new JSONObject();
		jso.put("id", clientId);
		jso.put("params", params);
		javaScriptSupport.addInitializerCall("carousel", jso);
	}
	
	public Block getCurrentParameterBlock(){
		return componentResources.getBlockParameter(currentParameterName);
	}
	
	public String getClientId(){
        return this.clientId;
    }
	
	
}
