package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class ContextMenuTest extends SeleniumTestCase2 {
	
	@Test
	public void testContextMenu() {
		
		open("/jquerycontextmenu");
		
		//We check if the context menu is displayed on right click...
		WebElement element = webDriver.findElement(convertLocator("//a[@class='linkContextMenu']"));
		new Actions(webDriver).contextClick(element);

		assertTrue(isElementPresent("//ul[@class='context-menu-list context-menu-root']"));
		
		//... And if the menu entries are rendered.
		assertTrue(isElementPresent("//li[@class='context-menu-item icon icon-edit']"));
	}
}
