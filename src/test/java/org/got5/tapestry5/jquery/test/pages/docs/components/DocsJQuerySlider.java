//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
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

package org.got5.tapestry5.jquery.test.pages.docs.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageReset;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

public class DocsJQuerySlider {

	@Inject
	private Request request;
	
	public List<JQueryTabData> getListTabData(){
		
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
		
	    listTabData.add(new JQueryTabData("Documentation","docs"));
	    
	    listTabData.add(new JQueryTabData("Example","example"));
	    
	    return listTabData;
	}
	
	@Persist
	@Property
	private int tabIndex;
	
	@PageReset
	public void resetTabIndex(){
		tabIndex=0;
	}
	
	public void onSubmit(){
		tabIndex=1;
	}

/*
 * First example : the simple slider
 */
@Property
@Persist
private int min, max;	

@Property
@Persist
private int val;

@Property
private JSONObject params;

@OnEvent(EventConstants.ACTIVATE)
public void initSlider(){
	if(min==0 && max==0) {
		max=150;
		min=10;
	}
	params=new JSONObject();
	params.put("value", val);
}


/*
 * Second example : the simple slider updating a zone
 */

@Property
@Persist
private int slideZone;

@Property
private JSONObject paramsZone;

@OnEvent(EventConstants.ACTIVATE)
public void initSliderZone(){
	paramsZone=new JSONObject();
	paramsZone.put("value", slideZone);
}

@Component
private Zone myZone;

@OnEvent(value=EventConstants.ACTION, component="sliderZone")
public Object returnZone(){
	String input = request.getParameter("slider");
	slideZone=Integer.parseInt(input);
	return myZone.getBody();
}
	

	
}
