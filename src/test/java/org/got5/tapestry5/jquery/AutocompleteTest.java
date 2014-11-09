package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class AutocompleteTest extends SeleniumTestCase{
	
	@Test
    public void testAutoComplete()
    {
        open("/ext/jqueryautocomplete");
        waitForPageToLoad();

        focus("identifier=foo");
        type("identifier=foo", "abcdeff");
        keyDown("identifier=foo", "e");
        keyUp("identifier=foo", "e");
        fireEvent("identifier=foo", "keydown");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("css=ul[class~=\"ui-autocomplete\"]") && isVisible("css=ul[class~=\"ui-autocomplete\"]");
            }
        }.wait("element not found!");

        assertEquals(true, isVisible("css=ul[class~=\"ui-autocomplete\"]"));
    }

}
