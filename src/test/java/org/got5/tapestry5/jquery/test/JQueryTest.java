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

package org.got5.tapestry5.jquery.test;

public class JQueryTest extends JavascriptTestSuite
{
    public JQueryTest()
    {
        super("src/test/jquery/webapp", "*googlechrome");
    }

    protected String getValidationElementLocator(String fieldId)
    {
        return "css=label[for=\"" + fieldId + "\"][generated=\"true\"]";
    }

    public void additionalValidationTest()
    {
        // nothing to do
    }

    @Override
    public String getCalendarPage()
    {
        return "/jquerycalendar";
    }

    @Override
    public String getCalendarDivSelector()
    {
        return "identifier=ui-datepicker-div";
    }

    @Override
    public String getCalendarField()
    {
        return "identifier=datefield";
    }

    @Override
    public String getAutocompleteDivSelector()
    {
        return "css=ul[class~=\"ui-autocomplete\"]";
    }

    @Override
    public String getAutocompleteField()
    {
        return "identifier=foo";
    }

    @Override
    public String getAutocompletePage()
    {
        return "/jqueryautocomplete";
    }


    @Override
    public String getPalettePage()
    {
        return "/jquerypalette";
    }
}
