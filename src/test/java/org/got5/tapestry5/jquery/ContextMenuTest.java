package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class ContextMenuTest extends SeleniumTestCase {
	
	@Test
	public void testContextMenu() {
		
		open("/jquerycontextmenu");
		
		//We check if the context menu is displayed on right click...
		mouseDownRight("//a[@class='linkContextMenu']");
		mouseUpRight("//a[@class='linkContextMenu']");
		assertTrue(isElementPresent("//ul[@class='context-menu-list context-menu-root']"));
		
		//... And if the menu entries are rendered.
		assertTrue(isElementPresent("//li[@class='context-menu-item icon icon-edit']"));
	}
}
