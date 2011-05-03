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

package org.got5.tapestry5.jquery.test.pages;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


public class CarouselPage{
	
	@Inject
	private Block flowerBlock;
	
	@Inject
	private JavaScriptSupport javascriptSupport;
	
	@Inject
	private ComponentResources resources;
	
	public JSONObject getAjaxParams(){
		JSONObject retour = new JSONObject();
		retour.put("wrap", "circular");
		String url = resources.createEventLink("ajaxEvent").toString();
		retour.put("itemLoadCallback", new JSONLiteral("loadCarousel('"+url+"')"));
		return retour;
	}
	
	public JSONObject getComplexParams(){
		JSONObject retour = new JSONObject();
		retour.put("wrap", "circular");
		return retour;
	}
	
	@AfterRender
	public void finish(){
		//javascriptSupport.addScript(InitializationPriority.LATE, "loadCarousel('%s')", );
	}
	
	public Object getTest(){
		return "hello";
	}
	
	@OnEvent("ajaxEvent")
	public Object ajaxtTest(){
		JSONArray retour = new JSONArray();
		retour.put("http://static.flickr.com/57/199481087_33ae73a8de_s.jpg");
		retour.put("http://static.flickr.com/75/199481072_b4a0d09597_s.jpg");
		retour.put("http://static.flickr.com/77/199481108_4359e6b971_s.jpg");
		retour.put("http://static.flickr.com/58/199481143_3c148d9dd3_s.jpg");
		retour.put("http://static.flickr.com/72/199481203_ad4cdcf109_s.jpg");
		retour.put("http://static.flickr.com/58/199481218_264ce20da0_s.jpg");
		retour.put("http://static.flickr.com/69/199481255_fdfe885f87_s.jpg");
		retour.put("http://static.flickr.com/60/199480111_87d4cb3e38_s.jpg");
		retour.put("http://static.flickr.com/70/229228324_08223b70fa_s.jpg"); 
		
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
