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

package org.got5.tapestry5.jquery.test.pages.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.json.JSONObject;

public class Autocomplete
{
    @Property
    private String foo;

    @OnEvent(component = "foo", value = "provideCompletions")
    public List<String> autoComplete(Object[] obj)
    {
    	String start = (String) obj[0];
    	
    	JSONObject json = (JSONObject) obj[1];
    	
    	List<String> strings = new ArrayList<String>();
        
        if (start != null && start.startsWith("abc"))
        {
            strings.add("abdcdke");
            strings.add("hgfdhgfhgf");
            strings.add("jklhjkhl");
            strings.add("vcxcvcx");
        }
        
        return strings;
    }
}
