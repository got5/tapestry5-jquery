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
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;


public class ImageCropperPage{
	
	@Property
	@Persist
	private int x, y, x2, y2, w, h;
	
	@Component
	private Zone myImageCropped;
	
	@Inject
	private Request request;

	@OnEvent(value=EventConstants.SELECTED, component="myImageCropper")
	public Object returnZoneRange(){
		x= Integer.parseInt(request.getParameter("x"));
		y= Integer.parseInt(request.getParameter("y"));	
		x2= Integer.parseInt(request.getParameter("x2"));
		y2= Integer.parseInt(request.getParameter("y2"));
		w= Integer.parseInt(request.getParameter("w"));
		h= Integer.parseInt(request.getParameter("h"));
		return myImageCropped.getBody();
	}
	
	
	
}
