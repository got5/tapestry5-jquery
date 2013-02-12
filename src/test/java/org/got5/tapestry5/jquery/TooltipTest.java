package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class TooltipTest extends SeleniumTestCase{
	
	/**
     * Test Method for Tooltip mixin
     */
    @Test
    public void testToolTip()
    {
    	open("/Tooltip");
    	
    	mouseOver("//section/p/a");
    	
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//div[@role='tooltip']");
            }
        }.wait("The tooltip is not visible", JQueryTestConstants.TIMEOUT);
    	
    	mouseOut("//section/p/a");
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return !isElementPresent("//div[@role='tooltip']");
            }
        }.wait("The tooltip is visible!", JQueryTestConstants.TIMEOUT);
        
    }
    
}	
