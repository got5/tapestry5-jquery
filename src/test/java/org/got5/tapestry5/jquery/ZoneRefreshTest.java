package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ZoneRefreshTest extends SeleniumTestCase{

    private static final String STOP = "//a[@id='stop']";

    private static final String START = "//a[@id='start']";

    @Test
	public void testZoneRefresh(){

		open("/ZoneRefresh");

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

		click(STOP);

		new Wait()
		{
			@Override
		    public boolean until()
		    {
				return getText("//div[@id='clickZone']").equals("2");
		    }
		}.wait("The Stop event of the ZoneRefresh mixin does not work well : expected 0 : " + getText("//div[@id='clickZone']"),
				JQueryTestConstants.TIMEOUT);

		click(START);

		new Wait()
		{
			@Override
		    public boolean until()
		    {
				return getText("//div[@id='clickZone']").equals("3");
		    }
		}.wait("The Start event of the ZoneRefresh mixin does not work well : expected 0 : " + getText("//div[@id='clickZone']"),
				JQueryTestConstants.TIMEOUT);
	}
}
