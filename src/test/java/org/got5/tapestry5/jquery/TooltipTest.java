package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.BeforeTest;
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
    
    @Test
    public void testI18NMessages(){
    	open("/Tooltip");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//a[@id='b']@title").contains("I18N");
            }
        }.wait("The link 'b' should have title attribute containg 'I18N'", JQueryTestConstants.TIMEOUT);
    }
    
    @Test
    public void testI18NMissingKey(){
    	open("/Tooltip");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//a[@id='c']@title").contains("missing key");
            }
        }.wait("The link 'c' should have title attribute containg 'missing key'", JQueryTestConstants.TIMEOUT);
    }
}	
