package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ButtonTest extends SeleniumTestCase2{
	@Test
    public void testButtonMixin(){
    	
    	open("/button");
    	waitForPageToLoad();
    	
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
