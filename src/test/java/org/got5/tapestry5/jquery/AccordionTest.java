package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class AccordionTest extends SeleniumTestCase{
	
	@Test
    public void testAccordion()
    {
        open("/jqueryaccordion");
        // active tab must be second
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 2");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
       
          //click on first tab
        click("xpath=/html/body/div/div/div/h3/a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 1");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

       
    }
	
	@Test
	public void testDefaultLabelForAccordion(){
		
		open("/jqueryaccordion");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//div[@id='accordion']/h3[4]/a").equals("Block Test");
            }
        }.wait("The fourth accordion does not have the right default value.", JQueryTestConstants.TIMEOUT);
	}
	
	@Test
    public void testAccordionOld()
    {
        open("/jqueryaccordionOld");
        // active tab must be second
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 2");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);
       
          //click on first tab
        click("xpath=/html/body/div/div/div/h3/a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 1");
            }
        }.wait("element not found!", JQueryTestConstants.TIMEOUT);

       
    }
}
