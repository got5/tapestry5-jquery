package org.got5.tapestry5.jquery.test;

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
    	open("/test/Tooltip");
    	
    	mouseOver("//div[@id='content']/a");
    	
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//div[@id='ui-tooltip-0']@aria-hidden").equals("false");
            }
        }.wait("The tooltip is not visible", 5000l);
    	
    	mouseOut("//div[@id='content']/a");
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//div[@id='ui-tooltip-0']@aria-hidden").equals("true");
            }
        }.wait("The tooltip is visible!", 5000l);
        
    }
    
}	
