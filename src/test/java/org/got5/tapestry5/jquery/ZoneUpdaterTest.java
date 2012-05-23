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
		focus("//input[@type='text'][@id='firstName']");
		type("//input[@type='text'][@id='firstName']", "Hempster");
		keyUp("//input[@type='text'][@id='firstName']", Keys.CONTROL.toString());
		focus("//input[@type='text'][@id='lastName']");
		type("//input[@type='text'][@id='lastName']", "Dempster");
		keyUp("//input[@type='text'][@id='lastName']", Keys.CONTROL.toString());
		new Wait() {
			@Override
			public boolean until() {
				return isElementPresent("//div[@id='nameZone'][contains(text(), 'Hempster Dempster')]");
			}
		}.wait("The tooltip is not visible", JQueryTestConstants.TIMEOUT);
	}
}
