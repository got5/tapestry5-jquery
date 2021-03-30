package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ZoneUpdaterTest extends SeleniumTestCase2 {

	@Test
	public void testZoneUpdater(){

		open("/ZoneUpdater");

		Actions actions = new Actions(webDriver);

		assertTrue(isElementPresent("//div[@id='nameZone'][contains(text(), 'Humpty Dumpty')]"));

		WebElement firstName = webDriver.findElement(convertLocator("//input[@type='text'][@id='firstName']"));
		WebElement lastName = webDriver.findElement(convertLocator("//input[@type='text'][@id='lastName']"));

		firstName.clear();
		actions.sendKeys(firstName, "Hempster").perform();

		waitForAjaxRequestsToComplete();

		lastName.clear();
		actions.sendKeys(lastName, "Dempster").perform();

		new Wait() {
			@Override
			public boolean until() {
				return getText("//*[@id='nameZone']").contains("Hempster Dempster");
				}
		}.wait("The Zone is not updated", JQueryTestConstants.TIMEOUT);
	}
}
