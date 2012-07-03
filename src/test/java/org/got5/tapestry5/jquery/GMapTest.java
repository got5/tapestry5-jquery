package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class GMapTest extends SeleniumTestCase{
	
	@Test
	public void testGoogleMapAvailable(){
		
		open("GMap");
		
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[starts-with(@class, 'gmno')]");
			}
		}.wait("The GoogleMap widget is not present. ", JQueryTestConstants.TIMEOUT);
	}
	
}
