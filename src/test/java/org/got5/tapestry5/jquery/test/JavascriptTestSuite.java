//
// Copyright 2011 GOT5 (GO Tapestry 5)
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

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public abstract class JavascriptTestSuite extends SeleniumTestCase
{

    @BeforeMethod
    public void adjustSpeed()
    {
        // it seems that integration test are unstable when speed is set to 0
        setSpeed("200");
    }

    /**
     * Zone
     */
    @Test
    public void testZone()
    {
        open("/test/zone");
        waitForPageToLoad();
        assertEquals(getText("identifier=myZone"), "Counting via AJAX : 0");

        click("identifier=myActionLink");
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isTextPresent("Counting via AJAX : 1");
            }
        }.wait("element not found");
        assertEquals(getText("identifier=myZone"), "Counting via AJAX : 1");
    }

    @Test
    public void testFormZone()
    {
        open("/test/zone");
        waitForPageToLoad();
        assertEquals(getText("identifier=myZone2"), "Dummy value is :");

        type("identifier=textfield", "dummy");
        click("identifier=submit");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isTextPresent("Dummy value is : dummy");
            }
        }.wait("element not found");

        assertTrue(getText("identifier=myZone2").contains("Dummy value is : dummy") && 
        			getText("identifier=myZone2").contains("I am a Component inside the block which is visible after the zone update"));
    }

    @Test
    public void testFormMultiZoneUpdate() 
    {
        open("/test/zone");
        waitForPageToLoad();
        assertEquals(getText("identifier=multiZone1"), "default zone content");
        assertEquals(getText("identifier=multiZone2"), "default zone content");
        
        click("identifier=multiSubmit");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isTextPresent("rendering block");
            }
        }.wait("element not found");

        assertEquals(getText("identifier=multiZone1"), "rendering block-1 after multi zone update");
        assertEquals(getText("identifier=multiZone2"), "rendering block-2 after multi zone update");
    }
    
    /**
     * Validation
     */
    @Test
    public void testValidation()
    {
        open("/test/validation");
        waitForPageToLoad();

        assertValidationWorking("field", "a", true, "submit");
        assertValidationWorking("field", "abcdefghijklmnopq", true, "submit");
        assertValidationWorking("field", "abcd", false, "submit");

        assertValidationWorking("field2", "0", true, "submit");
        assertValidationWorking("field2", "10", true, "submit");
        assertValidationWorking("field2", "3", false, "submit");

        additionalValidationTest();

        // can't test regular expressions and email, because jquery validate plugin does not support
        // it, or prototype does not support it
    }

    protected void assertValidationWorking(final String fieldId, String value, boolean validationVisible, String submitId)
    {
        focus("identifier=" + fieldId);
        type("identifier=" + fieldId, value);
        focus("identifier=" + submitId);

        if (validationVisible)
        {
            new Wait()
            {
                @Override
                public boolean until()
                {
                    return isVisible(getValidationElementLocator(fieldId));
                }
            }.wait("element not found");
        }

        assertEquals(validationVisible, isVisible(getValidationElementLocator(fieldId)));

    }

    protected abstract String getValidationElementLocator(String fieldId);

    public abstract void additionalValidationTest();

    /**
     * Calendar
     */
    @Test
    public void testCalendar()
    {
        open(getCalendarPage());
        waitForPageToLoad();

        click(getCalendarField());

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent(getCalendarDivSelector()) && isVisible(getCalendarDivSelector());
            }
        }.wait("element not found!");

        assertEquals(true, isVisible(getCalendarDivSelector()));
    }

    public abstract String getCalendarField();

    public abstract String getCalendarDivSelector();

    public abstract String getCalendarPage();

    /**
     * Autocomplete
     */
    @Test
    public void testAutoComplete()
    {
        open(getAutocompletePage());
        waitForPageToLoad();

        focus(getAutocompleteField());
        type(getAutocompleteField(), "abcdeff");
        keyDown(getAutocompleteField(), "e");
        keyUp(getAutocompleteField(), "e");
        fireEvent(getAutocompleteField(), "keydown");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent(getAutocompleteDivSelector()) && isVisible(getAutocompleteDivSelector());
            }
        }.wait("element not found!");

        assertEquals(true, isVisible(getAutocompleteDivSelector()));
    }

    public abstract String getAutocompletePage();

    public abstract String getAutocompleteField();

    public abstract String getAutocompleteDivSelector();

   // @Test
    public void testGridInPlace()
    {
        open("/test/grid");

        assertEquals(getText("css=tr.t-first td.firstName"), "lala010");

        click("css=th.firstName a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=tr.t-first td.firstName").equals("lala010");
            }
        }.wait("element not found!", 5000l);

        assertEquals(getText("css=tr.t-first td.firstName"), "lala010");

        click("css=th.firstName a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=tr.t-first td.firstName").equals("lala910");
            }
        }.wait("element not found!", 5000l);

        assertEquals(getText("css=tr.t-first td.firstName"), "lala910");

        click("css=th.age a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=tr.t-first td.age").equals("0");
            }
        }.wait("element not found!", 5000l);

        assertEquals(getText("css=tr.t-first td.age"), "0");

        click("css=th.age a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=tr.t-first td.age").equals("49");
            }
        }.wait("element not found", 5000l);

        assertEquals(getText("css=tr.t-first td.age"), "49");
    }

    /**
     * Palette
     */
    @Test
    public void testPalette()
    {
        open(getPalettePage());
        waitForPageToLoad();

        final String avail = "identifier=handling-avail";
        final String select = "identifier=handling-select";
        final String deselect = "identifier=handling-deselect";
        final String selected = "identifier=handling-selected";

        assertFalse(isEditable(select), select + " should not be clickable");
        assertFalse(isEditable(deselect), deselect + " should not be clickable");

        focus(avail);
        
        addSelection(avail, "label=Gift Basket");
        doubleClick(avail);
        
        assertFalse(isEditable(select), select + " should not be clickable");
        assertTrue(isEditable(deselect), deselect + " should be clickable");

        click(deselect);

        assertTrue(isEditable(select), select + " should be clickable");
        assertFalse(isEditable(deselect), deselect + " should not be clickable");

        
    }

    public abstract String getPalettePage();

    @Test
    public void testFormFragment()
    {
        open("/test/formfragment");
        waitForPageToLoad();

        String trigger = "identifier=separateShipTo";
        final String fragment = "identifier=seperateShippingAddress";

        assertFalse(isVisible(fragment), fragment + " should not be visible");

        click(trigger);
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isVisible(fragment);
            }
        }.wait(fragment + " should  be visible");


        assertTrue(isVisible(fragment), fragment + " should  be visible");

        click(trigger);
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return !isVisible(fragment);
            }
        }.wait(fragment + " should not be visible");

        assertFalse(isVisible(fragment), fragment + " should not be visible");
    }
    
    @Test
    public void testLinkSubmit()
    {
        open(getLinkSubmitPage());
        waitForPageToLoad();

        String field = "identifier=textfield";
        String link = "identifier=linksubmit";
        String result = "identifier=result";
        
        
        type(field, "dummy");
        click(link);
        
        waitForPageToLoad();

        assertEquals(getText(result), "dummy");
    }

    public abstract String getLinkSubmitPage();
    
    @Test
    public void testTabs()
    {
        open(getTabsPage());
        // active tab must be second
        assertEquals(getText("css=div#tabs-zone.t-zone h3"), "Panel 2");

        //click on first tab
        click("css=div.wrapper div#content div#tabs.ui-tabs ul.ui-tabs-nav li.ui-state-default a#eventlink");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 1");
            }
        }.wait("element not found!", 5000l);

        //click on third tab
        click("css=div.wrapper div#content div#tabs.ui-tabs ul.ui-tabs-nav li.ui-state-default a#eventlink_1");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 3");
            }
        }.wait("element not found!", 5000l);

        //click on last tab
        click("css=div.wrapper div#content div#tabs.ui-tabs ul.ui-tabs-nav li.ui-state-default a#eventlink_2");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 4");
            }
        }.wait("element not found!", 5000l);

       
    }
    
    public abstract String getTabsPage();
}
