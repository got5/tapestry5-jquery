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

package org.got5.tapestry5.jquery.test.pages.docs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.got5.tapestry5.jquery.utils.JQueryTabData;
@Import(library = "context:js/demo.js")
public class DocsZone
{
	@Inject
	private Request request;
	 
	public List<JQueryTabData> getListTabData()
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
	    listTabData.add(new JQueryTabData("Documentation","TabsBlock1"));
	    listTabData.add(new JQueryTabData("ActionLink","TabsBlock2"));
	    listTabData.add(new JQueryTabData("Form","TabsBlock3"));
	    listTabData.add(new JQueryTabData("MultiZoneUpdate","TabsBlock4"));
	    listTabData.add(new JQueryTabData("Custom Effects","TabsBlock5"));
	    listTabData.add(new JQueryTabData("Back to Prototype","TabsBlockLast"));
	    return listTabData;
	}
	
//Demo ActionLink
@Property
@Persist
private int count;

@Inject
private Block myBlockActionLink;

public Block getTheBlockActionLink()
{
	return myBlockActionLink;
}

@OnEvent(value = EventConstants.ACTION, component = "myActionLink")
Object updateCount()
{
	if (!request.isXHR()) {return this;}
	count++;
	return myBlockActionLink;
}


//Demo Form
@Property
@Persist
private String dummy;

@Inject
private Block myBlockForm;

public Block getTheBlockForm()
{
	return myBlockForm;
}

@OnEvent(value = EventConstants.SUCCESS, component = "myForm")
Object updateZoneContentFromForm()
{
	if (!request.isXHR()) {return this;}
	return myBlockForm;
}

//Demo MultiZoneUpdate
@Persist
private boolean afterFormSubmit;

@Property
private int blockId;

@InjectComponent
private Zone multiZone1, multiZone2;

@Inject
private Block defaultBlock, multiUpdateBlock;

@OnEvent(value = EventConstants.SUCCESS, component = "myMultiZoneUpdateForm")
Object performMultiZoneUpdate()
{
	afterFormSubmit = true;
	return new MultiZoneUpdate("multiZone1",
			multiZone1.getBody()).add("multiZone2", multiZone2.getBody());
}
    
public Block getMultiUpdateBlock1() {
	blockId = 1;
	return afterFormSubmit ? multiUpdateBlock : defaultBlock;
}
    
public Block getMultiUpdateBlock2() {
	blockId = 2;
	return afterFormSubmit ? multiUpdateBlock : defaultBlock;
}

//Demo Custom
@InjectComponent
private Zone myZoneCustom;

public JSONObject getZoneParams(){
	
	JSONObject ap = new JSONObject();
	
	ap.put("options",new JSONObject("direction","up"));

	ap.put("speed", 500);

	ap.put("callback", new JSONLiteral(String.format("callbackFunction()")));
	
	return ap;
}

@OnEvent(value = EventConstants.ACTION, component = "myActionLinkCustom")
Object cutomMixin()
{
	if (!request.isXHR()) {return this;}
	count++;
	return myZoneCustom.getBody();
}


}

