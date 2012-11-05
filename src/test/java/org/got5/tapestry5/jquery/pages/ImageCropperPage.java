//
// Copyright 2012 GOT5 (GO Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;


public class ImageCropperPage{
	
	@Property
	@Persist
	private float x, y, x2, y2, w, h;
	
	@Component
	private Zone myImageCropped;
	
	@Inject
	private Request request;
	
	@SetupRender
	private void setup() {
	    if(x==0) {
	        x=22.5f;
	        y=272.4f;
	        x2=305.16666f;
	        y2=378.4f;
	    }
	}

	public JSONObject getOptions(){
		
		JSONObject json = new JSONObject("aspectRatio", "2.777");
		Object[] initSize = {Float.valueOf(x),Float.valueOf(y),Float.valueOf(x2),Float.valueOf(y2)};
		JSONArray initSizetab = new JSONArray(initSize);
		json.put("setSelect", initSizetab);	
						
		return json;
	}

	@OnEvent(value=EventConstants.SELECTED, component="myImageCropper")
	public Object returnZoneRange(){
		x= Float.parseFloat(request.getParameter("x"));
		y= Float.parseFloat(request.getParameter("y"));	
		x2= Float.parseFloat(request.getParameter("x2"));
		y2= Float.parseFloat(request.getParameter("y2"));
		w= Float.parseFloat(request.getParameter("w"));
		h= Float.parseFloat(request.getParameter("h"));
		return myImageCropped.getBody();
	}
	
	
	
}
