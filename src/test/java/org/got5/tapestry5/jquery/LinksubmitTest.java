package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

public class LinksubmitTest extends SeleniumTestCase2{
	
	
    public void testLinkSubmit()
    {
        open("/ext/jquerylinksubmit");
        waitForPageToLoad();

        String field = "id=textfield";
        String link = "id=fred";
        String result = "id=result";
             
        type(field, "dummy");
        focus(field);
        keyPress(field,Keys.RETURN.toString());

        click("//*[@id='fred']");
        
        waitForPageToLoad();

        assertEquals(getText(result), "dummy");
    }


}
