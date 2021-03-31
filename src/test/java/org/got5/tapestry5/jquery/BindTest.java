package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class BindTest extends SeleniumTestCase2{
	
	@Test
    public void testBind(){
		open("/Bind");
        waitForPageToLoad();

		assertSourcePresent("tjq/bind","eventType","\"click\",");       
	}
	
	@Test
	public void testEvent() throws InterruptedException {
		open("/Bind");

        waitForPageToLoad();
		click("//section/a");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isTextPresent("click OK");
            }
        }.wait("element not found", JQueryTestConstants.TIMEOUT);
        
        assertEquals(getText("id=clickZone"), "click OK");
	}

}
