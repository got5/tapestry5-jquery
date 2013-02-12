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
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 2");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
        
        //click on first tab
        click("a#ui-id-1");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 1");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

        //click on third tab
        click("a#ui-id-2");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 3");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

        //click on last tab
        click("a#ui-id-3");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone.t-zone h3").equals("Panel 4");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
    }
	
	@Test
	public void testDefaultLabelForTab(){
		
		open("/jquerytabs");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("a#ui-id-4").contains("Block Test");
            }
        }.wait("The fourth label has not its default value", JQueryTestConstants.TIMEOUT);
        
	}

}
