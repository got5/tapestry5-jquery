package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class FormTest extends SeleniumTestCase{
	
	@Test
    public void testAjaxFormLoop(){
    	
    	open("/Form");
    	
    	click("//input[@id='test']");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getHtmlSource().contains("[\"test\",\"test\"]");
            }
        }.wait("The Submitted element is not the good one.", JQueryTestConstants.TIMEOUT);
        
        click("//input[@id='test2']");
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getHtmlSource().contains("[\"test2\",\"test2\"]");
            }
        }.wait("The Submitted element is not the good one.", JQueryTestConstants.TIMEOUT);
    }
}
