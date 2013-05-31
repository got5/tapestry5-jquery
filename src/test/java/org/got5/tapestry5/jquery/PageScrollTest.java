package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class PageScrollTest extends SeleniumTestCase {

    @Test
    public void page_scroll_works(){
        open("/PageScroll");

        assertTextPresent("Element#0");
        assertTextPresent("Element#2");

        assertFalse(isTextPresent("Element#300"));
        assertFalse(isTextPresent("Element#302"));

        this.runScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight," +
            "document.body.scrollHeight,document.documentElement.clientHeight));");

        sleep(1500);

        assertTrue(isTextPresent("Element#300"));
        assertTrue(isTextPresent("Element#302"));
    }

}
