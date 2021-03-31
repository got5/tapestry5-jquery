package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class AjaxFormLoopTest extends SeleniumTestCase2{
	
	@Test
    public void testAjaxFormLoop(){
    	
    	open("/AjaxFormLoop");
    	
    	click("//a[@data-afl-trigger='add']");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']").equals(2);
            }
        }.wait("We should have just one row.", 1000);
        
        
        click("//a[@data-afl-trigger='add']");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']").equals(3);
            }
        }.wait("A New row should be present." + getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']"), JQueryTestConstants.TIMEOUT);
        
        click("//a[@data-afl-behavior='remove'][1]");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']").equals(2);
            }
        }.wait("The first row should be deleted.", 1000);
    	

    }
}
