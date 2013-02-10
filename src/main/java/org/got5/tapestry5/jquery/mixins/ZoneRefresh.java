//
// Copyright 2010 GOT5 (GOTapestry5)
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

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;

/**
 * Auto-Updated Zone. 
 * If you want to start/stop refreshing the zone, you can trigger 
 * a stopRefreh/startRefresh events to the zone element.
 * 
 * @tapestrydoc
 */
@Events(JQueryEventConstants.REFRESH)
public class ZoneRefresh
{
   /**
    *  Period is seconds
    */
   @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
   private int period;

   /**
    * Context passed to the event
    */
   @Parameter
   private Object[] context;

   @InjectContainer
   private Zone zone;

   @Inject
   private JavaScriptSupport javaScriptSupport;

   @Inject
   private ComponentResources resources;

   @AfterRender
   void addJavaScript()
   {
      JSONObject params = new JSONObject();

      params.put("period", period);
      params.put("id", zone.getClientId());
      params.put("URL", createEventLink());

      javaScriptSupport.require("tjq/zone.refresh").with(params);
   }

   private Object createEventLink()
   {
      Link link = resources.createEventLink("zoneRefresh", context);
      return link.toAbsoluteURI();
   }
   
   Object onZoneRefresh()
   {
      CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
      resources.triggerEvent(JQueryEventConstants.REFRESH, context, callback);
      
      if(callback.getResult() != null){
         return callback.getResult();
      }
      
      return zone.getBody();
   }

}
