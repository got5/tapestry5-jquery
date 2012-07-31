package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class TreeTest extends SeleniumTestCase{
	
	@Test
	public void testTreeComponent(){
		
		open("/Tree");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return !isElementPresent("//div[@id='content']/div[@class='t-tree-container']/ul/li[1]/ul");
			}
		}.wait("The child none should be invisible", JQueryTestConstants.TIMEOUT);
		
		click("//div[@class='t-tree-container']/ul/li[1]/span[@class='t-tree-icon']");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[@class='t-tree-container']/ul/li[1]/ul");
				
			}
		}.wait("The child none should be visible", JQueryTestConstants.TIMEOUT);
		
		click("//div[@class='t-tree-container']/ul/li[1]/ul/li[1]/span[2]");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@class='t-tree-container']/ul/li[1]/ul/li[1]/span[2]@class").contains("t-selected-leaf-node-label");
			}
		}.wait("The none should be selected", JQueryTestConstants.TIMEOUT);
		
		
		click("//div[@class='t-tree-container']/ul/li[1]/span[@class='t-tree-icon t-tree-expanded']");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@class='t-tree-container']/ul/li[1]/ul@style").contains("none;");
			}
		}.wait("The child none should be invisible", JQueryTestConstants.TIMEOUT);
	
		
	}
}
