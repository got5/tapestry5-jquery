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

package org.got5.tapestry5.jquery.test.pages.docs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageReset;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

public class DocsValidation
{
    
	public List<JQueryTabData> getListTabData()
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
        listTabData.add(new JQueryTabData("Documentation","docs"));
        listTabData.add(new JQueryTabData("Example","example"));
        return listTabData;
    }
	
@Property
private String foo;

@Property
private Integer dummy;
    
@Property
private String email;
    
@Property
private String regexp;
    
@InjectComponent 
private Zone myZone;
    
@OnEvent(value=EventConstants.SUCCESS)
public Object onSuccess(){
	return myZone.getBody();
}
    
public Boolean getDataToDisplay(){
	return InternalUtils.isNonBlank(foo) && InternalUtils.isNonBlank(email) && InternalUtils.isNonBlank(regexp);
}
}
