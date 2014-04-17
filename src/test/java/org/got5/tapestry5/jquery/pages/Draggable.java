//
// Copyright 2010-2014 GOT5 (GO Tapestry 5)
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

import java.util.Date;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentEventLinkEncoder;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.Request;


public class Draggable
{
	@Persist
	@Property
	private String data;
	
		
	@Inject 
	ComponentResources resources;
	
	
	@Inject
	private ComponentEventLinkEncoder linkEncoder;
	
	@Inject
	private Request request;
	
		
	public Object onDrop(String contexte)
	{
		 // When you can't use @Component for each zone 
		 // get zone from the ComponentEventRequestParameter information  
		 ComponentEventRequestParameters parameters = linkEncoder.decodeComponentEventRequest(request);
		 String cptId = parameters.getNestedComponentId();
		 Zone toRefresh = (Zone)resources.getEmbeddedComponent(cptId);
		 data = contexte;
		 return toRefresh.getBody();		 
	}
	
	
	
	 public Date getNow()
	 {
		 return new Date();
	 }
}
