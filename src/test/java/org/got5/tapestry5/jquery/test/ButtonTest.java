package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ButtonTest extends SeleniumTestCase{
	@Test
    public void testButtonMixin(){
    	
    	open("/test/button");
    	
    	new Wait()
         {
             @Override
             public boolean until()
             {
                 return isElementPresent("//input[contains(@class,'ui-button')]");
             }
         }.wait("The jQueryUI Button should be present", JQueryTestConstants.TIMEOUT);
	}
}
