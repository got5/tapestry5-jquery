package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class TreeTest extends SeleniumTestCase2{
	
	@Test
	public void testTreeComponent(){
		
		open("/Tree");

		new Wait() {
			
			@Override
			public boolean until() {
				return !isElementPresent("//div[@class='t-tree-container']/ul/li[@class='t-last']/ul");
			}
		}.wait("The child none should not be invisible", JQueryTestConstants.TIMEOUT);
		
		click("//div[@class='tree-container']/ul/li[contains(@class, 'last')]/span[@class='tree-icon']");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[@class='tree-container']/ul/li[@class='last']/ul");
				
			}
		}.wait("The child none should be visible", JQueryTestConstants.TIMEOUT);
		
		click("//div[@class='tree-container']/ul/li[@class='last']/ul/li[contains(@class, 'last')]/span[2]");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@class='tree-container']/ul/li[contains(@class, 'last')]/ul/li[contains(@class, 'last')]/span[contains(@class, 'tree-label')]@class")
						.contains("selected-leaf-node");
			}
		}.wait("The none should be selected ", JQueryTestConstants.TIMEOUT);
		
		
		click("//div[@class='tree-container']/ul/li[contains(@class, 'last')]/span[contains(@class, 'tree-icon')]");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@class='tree-container']/ul/li[contains(@class, 'last')]/ul@style").contains("none;");
			}
		}.wait("The child none should be invisible", JQueryTestConstants.TIMEOUT);
	
		
	}
}
