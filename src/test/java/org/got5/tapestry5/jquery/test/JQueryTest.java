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

import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class JQueryTest extends JavascriptTestSuite
{

    public JQueryTest()
    {
    }

    @Override
    protected String getValidationElementLocator(String fieldId)
    {
        return "css=label[for=\"" + fieldId + "\"][generated=\"true\"]";
    }

    @Override
    public void additionalValidationTest()
    {
        // nothing to do
    }

    @Override
    public String getCalendarPage()
    {
        return "/test/calendar";
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
        return "/test/jqueryautocomplete";
    }

    @Override
    public String getPalettePage()
    {
        return "/test/jquerypalette";
    }

    @Override
    public String getLinkSubmitPage()
    {
        return "/test/jquerylinksubmit";
    }

    @Override
    public String getTabsPage()
    {
        return "/test/jquerytabs";
    }
    
    
    /**
     * jQuery exclusives components
     */
    @Test
    public void testAjaxUpload()
    {
        open("/test/AjaxUploadTest");

        final long start = System.currentTimeMillis();

        /*
         * As long as seleniums waits here you can test the component manually :)
         */
        new Wait()
        {
            @Override
            public boolean until()
            {
                return start + 2000 < System.currentTimeMillis();
            };
        }.wait("waited for a long time...", JQueryTestConstants.TIMEOUT);
    }
    
    @Test
    public void testAccordion()
    {
        open("/test/jqueryaccordion");
        // active tab must be second
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 2");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
       
          //click on first tab
        click("xpath=/html/body/div/div/div/h3/a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 1");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

       
    }
}
