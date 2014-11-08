package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class PlaceHolderTest extends SeleniumTestCase{

	@Test
	public void tesPlaceholderAttribute(){
		open("placeholder");
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//input[@id=\"textfield\"]@placeholder").equalsIgnoreCase("e.g. John Doe");

			}
		}.wait("The input should have a placeholder", JQueryTestConstants.TIMEOUT);
	}
}
