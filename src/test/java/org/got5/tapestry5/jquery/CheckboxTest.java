package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class CheckboxTest extends SeleniumTestCase{
	
	@Test
    public void testCheckboxComponent(){
    	
    	open("/Checkbox");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//form[@id='monForm']/fieldset/div/div/span@class").equals("ui-checkbox-icon");
            }
        }.wait("The checkbox should be unchecked", JQueryTestConstants.TIMEOUT);
    	
       click("//form[@id='monForm']/fieldset/div/label");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getAttribute("//form[@id='monForm']/fieldset/div/div/span@class").equals("ui-checkbox-icon ui-icon ui-icon-check");
            }
        }.wait("The checkbox should be checked", JQueryTestConstants.TIMEOUT);
    }
	
}
