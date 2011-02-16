//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
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

package org.got5.tapestry5.jquery.test;

public class PrototypeTest extends JavascriptTestSuite
{
    @Override
    protected String getValidationElementLocator(String fieldId)
    {
        return "identifier=" + fieldId + ":errorpopup";
    }

    public void additionalValidationTest()
    {
        assertValidationWorking("field4", "", true, "submit");
        assertValidationWorking("field4", "afoq", true, "submit");
        assertValidationWorking("field4", "foo", false, "submit");
    }

    @Override
    public String getCalendarPage()
    {
        return "/calendar";
    }

    @Override
    public String getCalendarDivSelector()
    {
        return "css=div[class=\"datePicker\"]";
    }

    @Override
    public String getCalendarField()
    {
        return "identifier=datefield-trigger";
    }

    @Override
    public String getAutocompleteDivSelector()
    {
        return "css=div[class=\"t-autocomplete-menu\"]";
    }

    @Override
    public String getAutocompleteField()
    {
        return "identifier=foo";
    }

    @Override
    public String getAutocompletePage()
    {
        return "/autocomplete";
    }

    @Override
    public String getPalettePage()
    {
        return "/palette";
    }

    @Override
    public String getLinkSubmitPage()
    {
        return "/linksubmit";
    }
    
    @Override
    public String getTabsPage()
    {
        return "/jquerytabs";
    }
}
