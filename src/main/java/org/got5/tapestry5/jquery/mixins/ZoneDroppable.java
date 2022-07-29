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

package org.got5.tapestry5.jquery.mixins;


import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * The droppable component 
 * 
 * @see <a href="http://docs.jquery.com/UI/Draggable">http://docs.jquery.com/UI/Draggable</a>
 * 
 * @tapestrydoc
 */
@SupportsInformalParameters
public class ZoneDroppable {
	
	
	   /**
		 * The Draggable parameters you want to override.
		 */
	   @Parameter
	   private JSONObject params;
	   
	   @InjectContainer
	   private Zone zone;
	   

	   @Inject
	   private JavaScriptSupport javaScriptSupport;

	   @Inject
	   private ComponentResources resources;

	   public ZoneDroppable()
	   {
	   }
	  
	   //For testing purpose
	   ZoneDroppable(Object [] context, ComponentResources resources,
	      JavaScriptSupport javaScriptSupport, Zone zone)
	   {
	      this.resources = resources;
	      this.javaScriptSupport = javaScriptSupport;
	      this.zone = zone;
	   }


	   private Object createEventLink()
	   {
	      Link link = resources.createEventLink("zoneDrop");
	      return link.toURI();
	   }
	   
	   Object onZoneDrop(Object[] context)
	   {
	      CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
	      resources.triggerEvent(JQueryEventConstants.DROP, context, callback);
	      
	      if(callback.getResult() != null){
	         return callback.getResult();
	      }
	      
	     return zone.getBody();
	   }
	
	   @AfterRender
	   void afterRender()
	   {
		
		   JSONObject data = new JSONObject();
        
		   JSONObject defaults = new JSONObject();
        
		   if (params == null)
			   params = new JSONObject();


		   JQueryUtils.merge(defaults, params);
		   data.put("params", defaults);
		   data.put("id", zone.getClientId());
		   data.put("BaseURL", createEventLink());

		   javaScriptSupport.require("tjq/ui").invoke("droppable").priority(InitializationPriority.LATE).with(data);
	   }

	
}
