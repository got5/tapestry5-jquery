package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ZoneUpdaterTest extends SeleniumTestCase {

	@Test(groups="whatevs")
	public void testZoneUpdater(){

		open("/ZoneUpdater");
		waitForPageToLoad();
		assertTrue(isElementPresent("//div[@id='nameZone'][contains(text(), 'Humpty Dumpty')]"));
		click("//input[@type='text'][@id='firstName']");
		focus("//input[@type='text'][@id='firstName']");
		type("//input[@type='text'][@id='firstName']", "Hempster");
		keyPress("//input[@type='text'][@id='firstName']", Keys.TAB.toString());
		click("//input[@type='text'][@id='lastName']");
		focus("//input[@type='text'][@id='lastName']");
		type("//input[@type='text'][@id='lastName']", "Dempster");
		keyPress("//input[@type='text'][@id='lastName']", Keys.TAB.toString());
		new Wait() {
			@Override
			public boolean until() {
				return getText("//*[@id='nameZone']").contains("Hempster Dempster");
				}
		}.wait("The Zone is not updated", JQueryTestConstants.TIMEOUT);
	}
}
