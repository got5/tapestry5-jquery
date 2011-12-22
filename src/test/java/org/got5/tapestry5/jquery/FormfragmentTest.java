package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class FormfragmentTest extends SeleniumTestCase{
	
	@Test
    public void testFormFragment()
    {
        open("/formfragment");
        waitForPageToLoad();

        String trigger = "identifier=separateShipTo";
        final String fragment = "//div[@id='seperateShippingAddress']";

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

        click(trigger);
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return !isVisible(fragment);
            }
        }.wait(fragment + " should not be visible");
    }

}
