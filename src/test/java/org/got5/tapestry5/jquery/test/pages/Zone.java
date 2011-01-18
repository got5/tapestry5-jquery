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
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class Zone
{
    @Property
    @Persist
    private int count;

    @Inject
    private Request request;

    @Inject
    private Block myBlockActionLink;

    @Inject
    private Block myBlockForm;
    
    @Inject
    private Block defaultBlock, multiUpdateBlock;

    @InjectComponent
    private org.apache.tapestry5.corelib.components.Zone multiZone1, multiZone2;
    
    @Property
    @Persist
    private String dummy;
    
    @Persist
    private boolean afterFormSubmit;
    
    @Property
    private int blockId;

    public Block getTheBlockActionLink()
    {
	return myBlockActionLink;
    }

    public Block getTheBlockForm()
    {
	return myBlockForm;
    }
    
    @OnEvent(value = "action", component = "myActionLink")
    Object updateCount()
    {
	if (!request.isXHR())
	{
	    return this;
	}
	count++;
	return myBlockActionLink;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "myForm")
    Object updateZoneContentFromForm()
    {
	if (!request.isXHR())
	{
	    return this;
	}

	return myBlockForm;
    }
    
    @OnEvent(value = EventConstants.SUCCESS, component = "myMultiZoneUpdateForm")
    Object performMultiZoneUpdate() 
    {
        afterFormSubmit = true;

        return new MultiZoneUpdate("multiZone1", multiZone1.getBody()).add("multiZone2", multiZone2.getBody()); 
    }
    
    public Block getMultiUpdateBlock1() {

        blockId = 1;
        
        return afterFormSubmit ? multiUpdateBlock : defaultBlock;
    }
    
    public Block getMultiUpdateBlock2() {

        blockId = 2;
        
        return afterFormSubmit ? multiUpdateBlock : defaultBlock;
    }
}
