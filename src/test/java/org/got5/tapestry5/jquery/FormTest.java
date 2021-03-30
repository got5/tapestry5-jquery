package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class FormTest extends SeleniumTestCase2{
	
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
