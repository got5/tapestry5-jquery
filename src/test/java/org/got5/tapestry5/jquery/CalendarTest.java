package org.got5.tapestry5.jquery;

import com.thoughtworks.selenium.Wait;
import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class CalendarTest extends SeleniumTestCase{
	
	@Test
    public void testCalendar()
    {
        open("/calendar");
        waitForPageToLoad();

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//button[@class='ui-datepicker-trigger']");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
        
        click("//button[@class='ui-datepicker-trigger']");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("identifier=ui-datepicker-div") && isVisible("identifier=ui-datepicker-div");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

        assertEquals(true, isVisible("identifier=ui-datepicker-div"));
    }
	
	@Test
	public void testCalendarInZone(){
		
		testLocalInZone("en", "Next");
        
		testLocalInZone("fr", "Suivant");
	}
	
	private void testLocalInZone(String locale, final String text){
		
        open("/" + locale + "/calendarinzone");
		
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//button[@class='ui-datepicker-trigger']");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
        
		click("//section/a[@data-update-zone='zone']");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//button[@class='ui-datepicker-trigger']");
            }
        }.wait("button[@class='ui-datepicker-trigger'] not found!", JQueryTestConstants.TIMEOUT);

        click("//button[@class='ui-datepicker-trigger']");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//a[contains(@class,'ui-datepicker-next')]@title").equalsIgnoreCase(text);
              
            }
        }.wait(text + " element not visible! " + getAttribute("//a[contains(@class,'ui-datepicker-next')]@title"), JQueryTestConstants.TIMEOUT);
        
	}

    @Test
    public void testCalendarDisabled() {

        open("/calendardisabled");
        waitForPageToLoad();

        assertEquals(false, isEditable("identifier=datefield"));

        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//button[@class='ui-datepicker-trigger']");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
        
        click("//button[@class='ui-datepicker-trigger']");

        assertEquals(false, isVisible("identifier=ui-datepicker-div"));

    }

}
