package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class CalendarTest extends SeleniumTestCase{
	
	@Test
    public void testCalendar()
    {
        open("/calendar");
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
	
	@Test
	public void testCalendarInZone(){
		
		testLocalInZone("en", "Next");
        
		testLocalInZone("fr", "Suivant");
	}
	
	private void testLocalInZone(String locale, final String text){
		
        open("/"+locale+"/calendarinzone");
		
		click("//a[@id='link']");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return !getAttribute("//div[contains(@class,'t-zone')]@style").contains("none");
            }
        }.wait("zone fr not visible!", JQueryTestConstants.TIMEOUT);

        click("//button[@class='ui-datepicker-trigger']");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//span[@class='ui-icon ui-icon-circle-triangle-e']").equalsIgnoreCase(text);
            }
        }.wait("element not visible!", JQueryTestConstants.TIMEOUT);
        
	}
}
