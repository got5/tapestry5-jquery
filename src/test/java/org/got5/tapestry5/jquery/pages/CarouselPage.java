//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
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

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;

public class CarouselPage{
	
	@Inject
	private Block flowerBlock;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	private AssetSource assetSource;
	
	
	
	
	
	public JSONObject getAjaxParams(){
		JSONObject retour = new JSONObject();
		retour.put("wrap", "circular");
		String url = resources.createEventLink("ajaxEvent").toString();
		retour.put("loadCallbackUrl", url);
		return retour;
	}
	
	public JSONObject getComplexParams(){
		JSONObject retour = new JSONObject();
		retour.put("wrap", "circular");
		return retour;
	}
	
	
	
	public Object getTest(){
		return "hello";
	}
	
	@OnEvent("ajaxEvent")
	public Object ajaxtTest(){
		JSONArray retour = new JSONArray();
		retour.put(assetSource.getContextAsset("img/flower_01.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_02.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_03.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_04.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_05.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_06.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_07.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_08.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_09.jpg",null).toClientURL());
		retour.put(assetSource.getContextAsset("img/flower_10.jpg",null).toClientURL());
		return retour;
	}
	
	@OnEvent("eventTest")
	public void testEvent(Object ctx){
		System.out.println(ctx);
	}
    
	@OnEvent("zoneTest")
	public Object zoneEvent(){
		return flowerBlock;
	}
	
	
	
}
