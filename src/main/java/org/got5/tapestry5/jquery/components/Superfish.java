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
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.javascript.SuperfishStack;



/**
 * The Component allows you to create a CSS drop-down menu
 *
 * @since 2.1.1
 * @see <a href="http://users.tpg.com.au/j_birch/plugins/superfish/">http://users.tpg.com.au/j_birch/plugins/superfish/</a>
 *
 * @tapestrydoc
 */
@Import(stack=SuperfishStack.STACK_ID)
@SupportsInformalParameters
public class Superfish{

	/**
	 * Id Client of your menu
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	/**
	 * JSON Object for the Superfish Configuration
	 */
	@Parameter
	private JSONObject params;

	/**
	 * Boolean parameter :
	 * 		true : your menu will be vertical
	 * 		false : your menu will have the navbar stylesheet
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private boolean vertical;

	/**
	 * CSS class for the menu. By default sf-menu
	 */
	@Parameter(value="sf-menu", defaultPrefix=BindingConstants.LITERAL)
	private String classe;

	/**
	 * Flag : Use the supersubs plugin
	 */
	@Parameter
	private boolean supersubs;

	/**
	 * JSON Object for the Supersubs configuration
	 */
	@Parameter
	private JSONObject supersubsParams;

	@Inject
	private ComponentResources componentResources;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	@Path("${assets.path}/components/superfish/css/superfish-vertical.css")
	private Asset verticalAsset;

	@Inject
	@Path("${assets.path}/components/superfish/css/superfish-navbar.css")
	private Asset navBarAsset;

	@Inject
	@Path("${assets.path}/components/superfish/css/superfish.css")
	private Asset mainAsset;

	@SetupRender
	public void init(MarkupWriter w){

	    this.clientId = javaScriptSupport.allocateClientId(componentResources);

	    javaScriptSupport.importStylesheet(mainAsset);

		String css = classe;

		if(componentResources.isBound("vertical"))
		{
			if(vertical) css += " sf-vertical";
			else css += " sf-navbar";

			if(vertical) javaScriptSupport.importStylesheet(verticalAsset);
			else javaScriptSupport.importStylesheet(navBarAsset);
		}

		w.element("ul","id",getClientId(),"class",css);

		componentResources.renderInformalParameters(w);

	}


	@AfterRender()
	public void finish(MarkupWriter w){
		w.end();

		JSONObject jso = new JSONObject();

		jso.put("id", getClientId());

		jso.put("classe", classe);

		jso.put("params", params);

		jso.put("supersubs", supersubs);

		jso.put("supersubsParams", supersubsParams);

		javaScriptSupport.addInitializerCall("superfish", jso);

	}

	public String getClientId(){
        return this.clientId;
    }

}
