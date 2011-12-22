package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class TabsTest extends SeleniumTestCase{
	
	@Test
    public void testTabs()
    {
        open("/jquerytabs");
        // active tab must be second
        assertEquals(getText("css=div#tabs-zone.t-zone h3"), "Panel 2");

        //click on first tab
        click("css=div.wrapper div#content div#tabs.ui-tabs ul.ui-tabs-nav li.ui-state-default a#eventlink");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 1");
            }
        }.wait("element not found!", 5000l);

        //click on third tab
        click("css=div.wrapper div#content div#tabs.ui-tabs ul.ui-tabs-nav li.ui-state-default a#eventlink_1");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 3");
            }
        }.wait("element not found!", 5000l);

        //click on last tab
        click("css=div.wrapper div#content div#tabs.ui-tabs ul.ui-tabs-nav li.ui-state-default a#eventlink_2");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 4");
            }
        }.wait("element not found!", 5000l);

       
    }

}
