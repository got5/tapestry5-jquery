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
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

public class DocsJQueryRangeSlider {

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
	
	public void onSubmit(){
		tabIndex = 1;
	}
	
	@PageReset
	public void resetTabIndex(){
		tabIndex = 0;
	}
	
/*
 * First example : the range slider
 */

@Property
@Persist
private int min, max;

@Property
private JSONObject rangeParams;


@OnEvent(EventConstants.ACTIVATE)
public void initRangeSlider(){
	rangeParams = new JSONObject();
	JSONArray values = new JSONArray();
	values.put(min);
	values.put(max);
	rangeParams.put("values", values);
	rangeParams.put("min", 0);
	rangeParams.put("max", 500);
}




/*
 * Second example : the range slider updating a zone
 */

@Property
@Persist
private int minZone, maxZone;

@Property
private JSONObject paramsRangeZone;

@OnEvent(EventConstants.ACTIVATE)
public void initRangeZoneSlider(){
	if(minZone==0 && maxZone==0) {
		maxZone=150;
		minZone=10;
	}
	
	paramsRangeZone=new JSONObject();
	JSONArray valuesZone = new JSONArray();
	valuesZone.put(minZone);
	valuesZone.put(maxZone);
	paramsRangeZone.put("values", valuesZone);
	paramsRangeZone.put("min", 0);
	paramsRangeZone.put("max", 200);
	paramsRangeZone.put("orientation", "vertical");
	paramsRangeZone.put("animate", "true");
}

@Component
private Zone myZoneRange;

@OnEvent(value=EventConstants.ACTION, component="sliderRangeZone")
public Object returnZoneRange(){
	minZone= Integer.parseInt(request.getParameter("min"));
	maxZone= Integer.parseInt(request.getParameter("max"));		
	return myZoneRange.getBody();
}
	
}
