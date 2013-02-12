package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class AjaxFormLoopTest extends SeleniumTestCase{
	
	@Test
    public void testAjaxFormLoop(){
    	
    	open("/AjaxFormLoop");
    	
    	click("//a[@data-afl-trigger='add']");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']").equals(1);
            }
        }.wait("We should have just one row.", JQueryTestConstants.TIMEOUT);
        
        
        click("//a[@data-afl-trigger='add']");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']").equals(2);
            }
        }.wait("A New row should be present." + getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']"), JQueryTestConstants.TIMEOUT);
        
        click("//a[@data-afl-behavior='remove'][1]");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[@data-container-type='core/ajaxformloop-fragment']").equals(1);
            }
        }.wait("The first row should be deleted.", JQueryTestConstants.TIMEOUT);
    	

    }
}
