package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class TreeTest extends SeleniumTestCase{
	
	@Test
	public void testTreeComponent(){
		
		open("/Tree");
		
		waitForPageInitialized();
		
		new Wait() {
			
			@Override
			public boolean until() {
				return !isElementPresent("//div[@class='t-tree-container']/ul/li[@class='t-last']/ul");
			}
		}.wait("The child none should not be invisible", JQueryTestConstants.TIMEOUT);
		
		click("//div[@class='t-tree-container']/ul/li[contains(@class, 't-last')]/span[@class='t-tree-icon']");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[@class='t-tree-container']/ul/li[@class='t-last']/ul");
				
			}
		}.wait("The child none should be visible", JQueryTestConstants.TIMEOUT);
		
		click("//div[@class='t-tree-container']/ul/li[@class='t-last']/ul/li[contains(@class, 't-last')]/span[2]");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@class='t-tree-container']/ul/li[contains(@class, 't-last')]/ul/li[contains(@class, 't-last')]/span[contains(@class, 't-tree-label')]@class")
						.contains("t-selected-leaf-node");
			}
		}.wait("The none should be selected ", JQueryTestConstants.TIMEOUT);
		
		
		click("//div[@class='t-tree-container']/ul/li[contains(@class, 't-last')]/span[contains(@class, 't-tree-icon')]");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@class='t-tree-container']/ul/li[contains(@class, 't-last')]/ul@style").contains("none;");
			}
		}.wait("The child none should be invisible", JQueryTestConstants.TIMEOUT);
	
		
	}
}
