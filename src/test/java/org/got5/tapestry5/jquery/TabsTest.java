package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class TabsTest extends SeleniumTestCase{
	
	@Test
    public void testTabs()
    {
        open("/jquerytabs");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//a[@id='ui-id-1']");
            }
        }.wait("The Tabs widget has not been initialized properly.", JQueryTestConstants.TIMEOUT);
        
        // active tab must be second
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone h3").equals("Panel 2");
            }
        }.wait("The second panel should be visible", JQueryTestConstants.TIMEOUT);
        
        //click on first tab
        click("//a[@id='ui-id-1']");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone h3").equals("Panel 1");
            }
        }.wait("The first panel should be visible", JQueryTestConstants.TIMEOUT);

        //click on third tab
        click("//a[@id='ui-id-3']");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone h3").equals("Panel 3");
            }
        }.wait("The third panel should be visible", JQueryTestConstants.TIMEOUT);

        //click on last tab
        click("//a[@id='ui-id-4']");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=div#tabs-zone h3").equals("Panel 4");
            }
        }.wait("The fourth panel should be visible", JQueryTestConstants.TIMEOUT);
    }
	
	@Test
	public void testDefaultLabelForTab(){
		
		open("/jquerytabs");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//a[@id='ui-id-1']");
            }
        }.wait("The Tabs widget has not been initialized properly.", JQueryTestConstants.TIMEOUT);
        
		new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//a[@id='ui-id-4']").contains("Block Test");
            }
        }.wait("The fourth label has not its default value", JQueryTestConstants.TIMEOUT);
        
	}

}
