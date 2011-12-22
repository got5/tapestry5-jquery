//
// Copyright 2010 GOT5 (GO Tapestry 5)
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

package org.got5.tapestry5.jquery.test.pages.test;

import java.util.HashMap;

import org.apache.tapestry5.json.JSONObject;



public class WidgetSlider
{
	private HashMap<String, JSONObject> test;
	
	/*public void setTest(JSONObject o){
		test = o;
	}
   public JSONObject getTest(){
	   test = new JSONObject("{orientation:'vertical'}");
	   return test;
   }*/
	
	public void setTest(HashMap<String, JSONObject> o){
		test = o;
	}
   public HashMap<String, JSONObject> getTest(){
	   JSONObject o = new JSONObject("{orientation:'vertical'}");
	   test = new HashMap<String, JSONObject>();
	   test.put("slider", o);
	   return test;
   }
	
}
