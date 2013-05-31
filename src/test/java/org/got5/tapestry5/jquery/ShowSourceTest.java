package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ShowSourceTest extends SeleniumTestCase{
	@Test
    public void testShowSource(){
    	
    	open("/ShowSource");
    	
    	 new Wait()
         {
             @Override
             public boolean until()
             {
                 return getXpathCount("//div[@class='my-snippet-container']").equals(2);
             }
         }.wait("We should have 2 showSource", JQueryTestConstants.TIMEOUT);
         
         new Wait()
         {
             @Override
             public boolean until()
             {
                 return isElementPresent("//pre[contains(@class,'sh_html')]");
             }
         }.wait("We should have 1 tml snippet", JQueryTestConstants.TIMEOUT);
         
         new Wait()
         {
             @Override
             public boolean until()
             {
            	 return isElementPresent("//pre[contains(@class,'sh_java')]");
             }
         }.wait("We should have 1 java snippet", JQueryTestConstants.TIMEOUT);
         
        
         new Wait()
         {
             @Override
             public boolean until()
             {
                 return getAttribute("//div[@class='sh_ide-anjuta snippet-wrap']@style").contains("none");
             }
         }.wait("The showSource should not be visible by default", JQueryTestConstants.TIMEOUT);
         
         click("//a[contains(@class,'snippet-toggle')]");
         
         new Wait()
         {
             @Override
             public boolean until()
             {
                 return getAttribute("//div[@class='sh_ide-anjuta snippet-wrap']@style").contains("block");
             }
         }.wait("The showSource should not be visible by default", JQueryTestConstants.TIMEOUT);
	}
}
