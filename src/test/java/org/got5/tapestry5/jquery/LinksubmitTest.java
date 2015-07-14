package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

public class LinksubmitTest extends SeleniumTestCase{
	
	
    public void testLinkSubmit()
    {
        open("/ext/jquerylinksubmit");
        waitForPageToLoad();

        String field = "identifier=textfield";
        String link = "identifier=fred";
        String result = "identifier=result";
             
        type(field, "dummy");
        focus(field);
        keyPress(field,Keys.RETURN.toString());

        click("//*[@id='fred']");
        
        waitForPageToLoad();

        assertEquals(getText(result), "dummy");
    }


}
