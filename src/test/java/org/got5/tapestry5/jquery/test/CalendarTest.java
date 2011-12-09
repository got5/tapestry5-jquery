package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class CalendarTest extends SeleniumTestCase{
	
	@Test
    public void testCalendar()
    {
        open("/test/calendar");
        waitForPageToLoad();

        click("identifier=datefield");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("identifier=ui-datepicker-div") && isVisible("identifier=ui-datepicker-div");
            }
        }.wait("element not found!");

        assertEquals(true, isVisible("identifier=ui-datepicker-div"));
    }
}
