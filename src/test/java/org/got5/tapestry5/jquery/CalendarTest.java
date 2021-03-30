package org.got5.tapestry5.jquery;

import com.thoughtworks.selenium.Wait;
import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CalendarTest extends SeleniumTestCase2 {

    @Test
    public void testCalendar() {
        open("/calendar");
        waitForPageToLoad();

        new Wait() {
            @Override
            public boolean until() {
                return isElementPresent("//button[@class='ui-datepicker-trigger']");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

        click("//button[@class='ui-datepicker-trigger']");

        new Wait() {
            @Override
            public boolean until() {
                return isElementPresent("id=ui-datepicker-div") && isVisible("id=ui-datepicker-div");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

        assertEquals(true, isVisible("id=ui-datepicker-div"));
    }

    @Test
    public void testCalendarInZone() {

        testLocalInZone("en", "Next");

        testLocalInZone("fr", "Suivant");
    }

    private void testLocalInZone(String locale, final String text) {

        open("/" + locale + "/calendarinzone");

        new Wait() {
            @Override
            public boolean until() {
                return isElementPresent("//button[@class='ui-datepicker-trigger']");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

        By datepickerTriggerLocator = convertLocator("//button[@class='ui-datepicker-trigger']");
        WebElement datepickerTrigger = webDriver.findElement(datepickerTriggerLocator);

        click("//section/a[@data-update-zone='zone']");

        waitForCondition(
                ExpectedConditions.and(
                        stalenessOf(datepickerTrigger),
                        presenceOfElementLocated(datepickerTriggerLocator)),
                "button[@class='ui-datepicker-trigger'] not found!");

        click(datepickerTriggerLocator);

        new Wait() {
            @Override
            public boolean until() {
                return getAttribute("//a[contains(@class,'ui-datepicker-next')]@title").equalsIgnoreCase(text);
            }
        }.wait(text + " element not visible! " + getAttribute("//a[contains(@class,'ui-datepicker-next')]@title"), JQueryTestConstants.TIMEOUT);
    }

    @Test
    public void testCalendarDisabled() {

        open("/calendardisabled");

        assertFalse(isEditable("id=datefield"));

        new Wait() {
            @Override
            public boolean until() {
                return isElementPresent("//button[@class='ui-datepicker-trigger']");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

        click("//button[@class='ui-datepicker-trigger']");

        assertFalse(isVisible("id=ui-datepicker-div"));
    }

}
