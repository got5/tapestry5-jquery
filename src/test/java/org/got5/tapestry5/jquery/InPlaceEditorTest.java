package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class InPlaceEditorTest extends SeleniumTestCase{

	@Test
	public void testInPlaceEditor(){
		
		open("/DocsInPlaceEditor");
		
		click("//table/tbody/tr[@class='t-first']/td[@class='lastName']/span");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//table/tbody/tr[@class='t-first']/td[@class='lastName']/span/form");
            }
        }.wait("The input is not visible", JQueryTestConstants.TIMEOUT);
        
        type("//table/tbody/tr[@class='t-first']/td[@class='lastName']/span/form/input", "François");
        
        click("//table/tbody/tr[@class='t-first']/td[@class='lastName']/span/form/button[@type='submit']");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//table/tbody/tr[@class='t-first']/td[@class='lastName']/span").contains("François");
            }
        }.wait("The value has not been updated", JQueryTestConstants.TIMEOUT);
	}
}
