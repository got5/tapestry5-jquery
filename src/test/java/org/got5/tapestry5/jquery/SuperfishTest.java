package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class SuperfishTest extends SeleniumTestCase{
	
	@Test
    public void testSuperfishComponent(){
    	
    	open("/SuperFish");
    	

    	new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//ul[@id='menu1'][contains(@class,'sf-menu')]");
            }
        }.wait("The ul element should have the sf-menu class : " + getAttribute("//ul[@id='menu1']@class"), JQueryTestConstants.TIMEOUT);
        
        click("//ul[@id='menu1']/li/a[@href='#a']");
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//ul[@id='menu1']/li[1]@class").contains("sfHover");
            }
        }.wait("The ul element should have the sfHover class : " + getAttribute("//ul[@id='menu1'][contains(@class,'sf-menu')]/li[1]@class"), JQueryTestConstants.TIMEOUT);
        
        click("//ul[@id='menu1']/li[2]/a");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return !getAttribute("//ul[@id='menu1']/li[1]@class").contains("sfHover");
            }
        }.wait("The ul element should not have the sfHover class : " + getAttribute("//ul[@id='menu1'][contains(@class,'sf-menu')]/li[1]@class"), JQueryTestConstants.TIMEOUT);
    }
	
}
