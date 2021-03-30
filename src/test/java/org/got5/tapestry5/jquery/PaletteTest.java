package org.got5.tapestry5.jquery;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class PaletteTest extends SeleniumTestCase2 {
	
	@Test
    public void testPalette() throws InterruptedException {
        open("/ext/jquerypalette");

        final By avail = By.id("handling-avail");
        final By select = By.id("handling-select");
        final By deselect = By.id("handling-deselect");

        assertFalse(isEnabled(select), select + " should not be clickable");
        assertFalse(isEnabled(deselect), deselect + " should not be clickable");

        focus(avail);

        WebElement availElement = webDriver.findElement(avail);

        Select selectElement = new Select(availElement);
        selectElement.selectByVisibleText("Gift Basket");
        Actions actions = new Actions(webDriver);
        actions.doubleClick(availElement).perform();
        //  Sometimes doubleClick may keep another option selected after action
        selectElement.deselectAll();

        assertFalse(isEnabled(select), select + " should not be clickable");
        assertTrue(isEnabled(deselect), deselect + " should be clickable");

        click(deselect);

        assertTrue(isEnabled(select), select + " should be clickable");
        assertFalse(isEnabled(deselect), deselect + " should not be clickable");
    }
}
