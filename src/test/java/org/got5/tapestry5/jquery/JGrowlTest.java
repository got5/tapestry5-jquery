package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class JGrowlTest extends SeleniumTestCase2{
	
	@Test
	public void testTransiant(){
		
		open("/jgrowl");
		
		click("//a[contains(@href, 'jgrowl.transient')]");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[contains(@class, 'jgrowl-info')]") &&
						isElementPresent("//div[contains(@class, 'jgrowl-warn')]") &&
						isElementPresent("//div[contains(@class, 'jgrowl-error')]");
			}
		}.wait("The jGrowl messages are missing.", JQueryTestConstants.TIMEOUT);
		
		/*new Wait() {
			
			@Override
			public boolean until() {
				return !(isElementPresent("//div[contains(@class, \"jgrowl-info\")]") &&
						isElementPresent("//div[contains(@class, \"jgrowl-warn\")]") &&
						isElementPresent("//div[contains(@class, \"jgrowl-error\")]"));
			}
		}.wait("The jGrowl messages are always displayed.", 20000l);*/
		
	}
	
	@Test(dependsOnMethods="testTransiant")
	public void testNonTransiant(){
		
		open("/jgrowl");
		
		click("//a[contains(@href, 'jgrowl.untildismiss')]");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[contains(@class, 'jgrowl-info')]") &&
						isElementPresent("//div[contains(@class, 'jgrowl-warn')]") &&
						isElementPresent("//div[contains(@class, 'jgrowl-error')]");
			}
		}.wait("The jGrowl messages are missing.", JQueryTestConstants.TIMEOUT);
		
		open("/jgrowl");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[contains(@class, 'jgrowl-info')]") &&
						isElementPresent("//div[contains(@class, 'jgrowl-warn')]") &&
						isElementPresent("//div[contains(@class, 'jgrowl-error')]");
			}
		}.wait("The jGrowl messages should be visible after refreshing.", JQueryTestConstants.TIMEOUT);
	}

}
