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
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

public class Autocomplete
{
    @Property
    private String foo;
    
    @Property
    private List<String> list;
    
    @OnEvent(EventConstants.ACTIVATE)
    public void createList(){
    	list = new ArrayList<String>();
    	
    	list.add("Accordion");
    	list.add("AjaxFormLoop");
    	list.add("AjaxUpload");
    	list.add("Autocomplete");
    	list.add("Button");
    	list.add("Carousel");
    	list.add("Checkbox");
    	list.add("CustomDatepicker");
    	list.add("Datefield");
    	list.add("Dialog");
    	list.add("FormFragment");
    	list.add("LinkSubmit");
    	list.add("Mask");
    	list.add("Palette");
    	list.add("Range Slider");
    	list.add("Reveal");
    	list.add("Slider");
    	list.add("Superfish menu");
    	list.add("Tabs");
    	list.add("Tooltip");
    	list.add("Validation");
    	list.add("Zone");
    }
    
    @OnEvent(component = "foo", value = "provideCompletions")
    public List<String> autoComplete(String start)
    {
        List<String> strings = new ArrayList<String>();
        
        if (start != null)
        {
            for(String value : list){
            	if(value.toUpperCase().startsWith(start.toUpperCase())) strings.add(value);
            }
        }
        
        return strings;
    }
}
