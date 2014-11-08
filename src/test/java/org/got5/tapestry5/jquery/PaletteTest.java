package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class PaletteTest extends SeleniumTestCase{
	
	@Test
    public void testPalette()
    {
        open("/ext/jquerypalette");
        waitForPageToLoad();

        final String avail = "identifier=handling-avail";
        final String select = "identifier=handling-select";
        final String deselect = "identifier=handling-deselect";
        final String selected = "identifier=handling-selected";

        assertFalse(isEditable(select), select + " should not be clickable");
        assertFalse(isEditable(deselect), deselect + " should not be clickable");

        focus(avail);
        
        addSelection(avail, "label=Gift Basket");
        doubleClick(avail);
        
        assertFalse(isEditable(select), select + " should not be clickable");
        assertTrue(isEditable(deselect), deselect + " should be clickable");

        click(deselect);

        assertTrue(isEditable(select), select + " should be clickable");
        assertFalse(isEditable(deselect), deselect + " should not be clickable");

        
    }
}
