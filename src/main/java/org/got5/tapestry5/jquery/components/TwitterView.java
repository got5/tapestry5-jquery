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

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;



/**
 * @since 2.1.1
 */
@Import(library = {
			"${assets.path}/components/twitterview/jquery.twitterview.js",
			"${assets.path}/components/twitterview/twitterview.js"
		},
		stylesheet = {
			"${assets.path}/components/twitterview/twitterview.css"
		}
)
@SupportsInformalParameters
public class TwitterView implements ClientElement {
	 
	
	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;
	
	@Parameter
	private JSONObject params;
	
	@Parameter(value="gotapestry5", defaultPrefix = BindingConstants.LITERAL)
	private String account;
	
	@Parameter(value="10", defaultPrefix = BindingConstants.LITERAL)
	private int count;
	
	@Parameter(value="true", defaultPrefix = BindingConstants.LITERAL)
	private boolean includeRetweets;
	
	@Parameter(value="jquery-twitterview", defaultPrefix = BindingConstants.LITERAL)
	private String className;
	
	@Inject
	@Path("${assets.path}/components/twitterview/loader.gif")
	private Asset loaderImage;
	
	
	
	@Inject
	private ComponentResources componentResources;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private Messages messages;
	
	
	
	@SetupRender
	public void init(MarkupWriter w){
		w.element("div", "id", clientId, "class", className);
		componentResources.renderInformalParameters(w);
		//If javascript is disabled, it shows a link
		w.element("a", "href","http://www.twitter.com/"+account, "target","_blank");
		w.write(String.format(messages.get("twitterview-alt-text"),account));
		w.end();
		w.end();
	}
	
	@AfterRender()
	public void finish(MarkupWriter w){
		JSONObject jso = new JSONObject();
		jso.put("id", clientId);
		if(params==null){
			params = new JSONObject();
		}
		//Explicit parameters override parameters passed through the 'params' parameter
		params.put("account",account);
		params.put("count", count);
		params.put("includeRetweets", includeRetweets?"1":"0");
		
		if(params.isNull("errorMessage")){
			params.put("errorMessage",messages.get("twitterview-error-text"));
		}
		
		if(params.isNull("loader") && loaderImage!=null){
			params.put("loader", loaderImage.toClientURL());
		}
		jso.put("params", params);
		javaScriptSupport.addInitializerCall("twitterView", jso);
		
	}
	
	public String getClientId(){
        return this.clientId;
    }
}
