package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ZoneRefreshTest extends SeleniumTestCase{

	@Test
	public void testZoneRefresh() throws Exception{
		
		open("/ZoneRefresh");
		
		
		Thread.sleep(1000l);
		click("//a[@id='stop']");
		Thread.sleep(1000l);
		final String count = getText("//div[@id='clickZone']");
		Thread.sleep(1000l);
		new Wait()
		{
			@Override
		    public boolean until()
		    {
				return getText("//div[@id='clickZone']").equals(count);
		    }
		}.wait("The Stop event of the ZoneRefresh mixin does not work well : expected "+count+" : " + getText("//div[@id='clickZone']"), 
				JQueryTestConstants.TIMEOUT);
		
		click("//a[@id='start']");
		Thread.sleep(1000l);
		
		new Wait()
		{
			@Override
		    public boolean until()
		    {
				return !getText("//div[@id='clickZone']").equals(count);
		    }
		}.wait("The Start event of the ZoneRefresh mixin does not work well : not expected "+count+" : " + getText("//div[@id='clickZone']"), 
				JQueryTestConstants.TIMEOUT);
	}
}
