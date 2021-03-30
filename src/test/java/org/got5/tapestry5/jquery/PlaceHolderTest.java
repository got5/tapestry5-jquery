package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class PlaceHolderTest extends SeleniumTestCase2{

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
