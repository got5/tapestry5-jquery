package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class RevealTest extends SeleniumTestCase{
	@Test
    public void testRevealMixin(){
    	
    	open("/reveal");
    	
    	click("//a[@name='monLink2']");
    	
    	testModal();
    	
    }
	
	@Test
    public void testRevealMixinWithSelector(){
    	
    	open("/reveal");
    	
    	click("//a[@name='monLink']");
    	
    	testModal();
    	
    }
	
	private void testModal(){
   	 new Wait()
     {
         @Override
         public boolean until()
         {
             return getAttribute("//div[@class='reveal-modal']@style").contains("visible");
         }
     }.wait("The reveal window is not visible", JQueryTestConstants.TIMEOUT);
     
     click("//div[@class='reveal-modal-bg']");
     
    
	 new Wait()
     {
         @Override
         public boolean until()
         {
             return getAttribute("//div[@class='reveal-modal']@style").contains("hidden");
         }
     }.wait("The reveal window visible", JQueryTestConstants.TIMEOUT);
	}
}
