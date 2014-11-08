package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class LinksubmitTest extends SeleniumTestCase{
	
	@Test
    public void testLinkSubmit()
    {
        open("/ext/jquerylinksubmit");
        waitForPageToLoad();

        String field = "identifier=textfield";
        String link = "identifier=fred";
        String result = "identifier=result";
        
        
        type(field, "dummy");
        click(link);
        
        waitForPageToLoad();

        assertEquals(getText(result), "dummy");
    }

}
