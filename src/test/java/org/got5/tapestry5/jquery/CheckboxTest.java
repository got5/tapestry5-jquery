package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class CheckboxTest extends SeleniumTestCase2{
	
	@Test
    public void testCheckboxComponent(){
    	
    	open("/Checkbox");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//div[contains(@class, 'ui-checkbox-box')]/span");
            }
        }.wait("The checkbox is not initialized", JQueryTestConstants.TIMEOUT);
        
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//div[contains(@class, 'ui-checkbox-box')]/span@class").equals("ui-checkbox-icon");
            }
        }.wait("The checkbox should be unchecked", JQueryTestConstants.TIMEOUT);
    	
       click("//form/fieldset/div/label");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getAttribute("//div[contains(@class, 'ui-checkbox-box')]/span@class").equals("ui-checkbox-icon ui-icon ui-icon-check");
            }
        }.wait("The checkbox should be checked", JQueryTestConstants.TIMEOUT);
    }
	
}
