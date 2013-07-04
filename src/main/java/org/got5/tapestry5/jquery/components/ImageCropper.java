//
// Copyright 2012 GOT5 (GO Tapestry 5)
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

import javax.inject.Inject;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeforeRenderBody;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
* @since 3.3.1
* @see <a href="http://deepliquid.com/content/Jcrop.html">jCrop</a>
* 
* @tapestrydoc
*/
@Import(library = {"${assets.path}/components/jcrop/jquery.Jcrop.js",
				   "${assets.path}/components/jcrop/imagecropper.js"},
	    stylesheet={"${assets.path}/components/jcrop/jquery.Jcrop.css"})

@SupportsInformalParameters
public class ImageCropper implements ClientElement{
	
	@Environmental
    private JavaScriptSupport _support;

	@Inject
    private ComponentResources _resources;

	/**
	 * The image asset to render.
	 * @deprecated Please use directly the asset parameter
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String _src;
	
	/**
	 * @deprecated Please use directly the asset parameter
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "context")
	private String _domain;

	/**
	 * The image asset to render.
	 */
	@Parameter(defaultPrefix = BindingConstants.ASSET)
    private Asset asset;
	
	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;
	
	/**
     * The zone to update when onSelect event occured on the jcrop. 
     * An "select" event is triggered on the server. 
     * You can catch it on your page with @OnEvent(value=EventConstants.SELECTED, component="ImageCropper").
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String zone;
	
    /**
	 * JSON options for the jcrop component
	 * awaited options are 
	 * - aspectRatio to define the ratio
	 * - setSelect array of value for x,y,x2,y2
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private JSONObject options;

    
	@Inject
	private AssetSource assetSource;

	
	
	@BeginRender
	void begin(MarkupWriter writer)
	{
	    String clientId = _support.allocateClientId(_resources.getId());
	    
	    Asset image = asset;
	    if(!_resources.isBound("asset"))
	    	image = assetSource.getAsset(null, _domain + ":" + _src, null);
	    
	    
	    writer.element("img", "src", image.toClientURL(), "id", clientId);

	    _resources.renderInformalParameters(writer);
    }

	@BeforeRenderBody
	boolean beforeRenderBody()
	{
		return false;
	}

	@AfterRender
	void after(MarkupWriter writer)
	{
		writer.end();
		JSONObject jso = new JSONObject();
		jso.put("id", clientId);
		//jso.put("params", params);
		if(_resources.isBound("zone")){
    		Link link = _resources.createEventLink(EventConstants.SELECTED);
    		jso.put("url", link.toAbsoluteURI());
    		jso.put("zoneId", zone);
    	}
		
		JQueryUtils.merge(jso, options);
		
		_support.addInitializerCall("imageCropper", jso);
	}

	public String getClientId() {
		return this.clientId;
	}

}
