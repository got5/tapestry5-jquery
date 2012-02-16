package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ZoneRefreshTest extends SeleniumTestCase{

	@Test
	public void testZoneRefresh(){
		
		open("/ZoneRefresh");
		
		new Wait()
		{
			@Override
		    public boolean until()
		    {
				return getText("//div[@id='clickZone']").equals("0");
		    }
		}.wait("The ZoneRefresh mixin does not work well : expected 0 : " + getText("//div[@id='clickZone']"), 
				JQueryTestConstants.TIMEOUT);
		
		new Wait()
		{
			@Override
		    public boolean until()
		    {
				return getText("//div[@id='clickZone']").equals("1");
		    }
		}.wait("The ZoneRefresh mixin does not work well : expected 0 : " + getText("//div[@id='clickZone']"), 
				JQueryTestConstants.TIMEOUT);
		
		new Wait()
		{
			@Override
		    public boolean until()
		    {
				return getText("//div[@id='clickZone']").equals("2");
		    }
		}.wait("The ZoneRefresh mixin does not work well : expected 0 : " + getText("//div[@id='clickZone']"), 
				JQueryTestConstants.TIMEOUT);
		   
	}
}
