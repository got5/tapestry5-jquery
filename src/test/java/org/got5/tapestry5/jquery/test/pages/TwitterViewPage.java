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

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.util.TextStreamResponse;



public class TwitterViewPage{
	
	
	@Inject
	@Path("${tapestry.jquery.path}/twitterview/dataJSONPTwitter.json")
	private Asset fakeData;
	
	@Inject
	private ComponentResources componentResources;
	
	
	@OnEvent("loadFakeData")
	public Object loadFakeData() throws IOException{
		InputStream fileStream = fakeData.getResource().openStream();
		String content = IOUtils.toString(fileStream);
		fileStream.close();
		return new TextStreamResponse("json", content);
	}
	
	public JSONObject getParams(){
		JSONObject params = new JSONObject();
		params.put("timelineUrl", componentResources.createEventLink("loadFakeData").toURI());
		return params;
	}
	
}
