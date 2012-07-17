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

	/**
	 * When a form fragment par is hidden, it's data shouldn't be submitted and it shouldn't block validation 
	 */
	@Test
    public void testFormFragmentSubmissionFilter()
    {
        open("/formfragment");
        waitForPageToLoad();
        type("address", "_address_");
        type("code", "bad_value");
        
        
        click("//input[@type='submit']");
        waitForPageToLoad();
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return "0".equals(getValue("code")) /* default value*/ && "_address_".equals(getValue("address")) /* value submitted*/ ;
            }
        }.wait("Code bad_value shouldn't have been submitted and shouldn't block submission");
        

    }
}
