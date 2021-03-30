package org.got5.tapestry5.jquery;

import com.thoughtworks.selenium.Wait;
import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class AutocompleteTest extends SeleniumTestCase2{
	
	@Test
    public void testAutoComplete()
    {
        open("/ext/jqueryautocomplete");
        waitForPageToLoad();

        focus("id=foo");
        type("id=foo", "abcdeff");
        new Actions(webDriver).sendKeys("e").perform();

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
