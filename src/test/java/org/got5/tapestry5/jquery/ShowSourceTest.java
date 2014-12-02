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
                 return isElementPresent("//div[@id='tml']//div[starts-with(@class,'CodeMirror')]")
                         && isElementPresent("//div[@id='tml']/div[@class='my-snippet-container']");
             }
         }.wait("We should have 1 tml snippet", JQueryTestConstants.TIMEOUT);

         new Wait()
         {
             @Override
             public boolean until()
             {
                 return isElementPresent("//div[@id='java']//div[starts-with(@class,'CodeMirror')]")
                         && isElementPresent("//div[@id='java']/div[@class='my-snippet-container']");
             }
         }.wait("We should have 1 java snippet", JQueryTestConstants.TIMEOUT);

         assertFalse(isVisible("//div[@id='java']//a[contains(@class, 'hide')]"), "The hide button should not be visible by default.");
         assertFalse(isVisible("//div[@id='java']/div[@class='my-snippet-container']"), "The java snippet should not be visible by default.");

         click("//div[@id='java']//a[contains(@class, 'show')]");

         new Wait()
         {
             @Override
             public boolean until()
             {
                 return !isVisible("//div[@id='java']//a[contains(@class, 'show')]")
                         && isVisible("//div[@id='java']//a[contains(@class, 'hide')]")
                         && isVisible("//div[@id='java']/div[@class='my-snippet-container']");
             }
         }.wait("The show button should not be visible; hide button and snippet should have been visible.", JQueryTestConstants.TIMEOUT);

         click("//div[@id='java']//a[contains(@class, 'hide')]");

         new Wait()
         {
             @Override
             public boolean until()
             {
                 return isVisible("//div[@id='java']//a[contains(@class, 'show')]")
                         && !isVisible("//div[@id='java']//a[contains(@class, 'hide')]")
                         && !isVisible("//div[@id='java']/div[@class='my-snippet-container']");
             }
         }.wait("The hide button and java snippet should not be visible after clicking on the hide button", JQueryTestConstants.TIMEOUT);
	}
}
