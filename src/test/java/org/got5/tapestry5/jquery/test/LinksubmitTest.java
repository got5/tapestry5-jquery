package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class LinksubmitTest extends SeleniumTestCase{
	
	@Test
    public void testLinkSubmit()
    {
        open("/test/jquerylinksubmit");
        waitForPageToLoad();

        String field = "identifier=textfield";
        String link = "identifier=linksubmit";
        String result = "identifier=result";
        
        
        type(field, "dummy");
        click(link);
        
        waitForPageToLoad();

        assertEquals(getText(result), "dummy");
    }

}
