package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class RevealTest extends SeleniumTestCase{
	@Test
    public void testRevealMixin(){
    	
    	open("/test/reveal");
    	
    	click("//a[@id='pagelink']");
    	
    	 new Wait()
         {
             @Override
             public boolean until()
             {
                 return getAttribute("//div[@class='reveal-modal']@style").contains("visible");
             }
         }.wait("The reveal window is not visible", 5000l);
         
         click("//div[@class='reveal-modal-bg']");
         
        
    	 new Wait()
         {
             @Override
             public boolean until()
             {
                 return getAttribute("//div[@class='reveal-modal']@style").contains("hidden");
             }
         }.wait("The reveal window visible", 5000l);
    	
    }
}
