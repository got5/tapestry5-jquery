package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class AccordionTest extends SeleniumTestCase{
	
	@Test
    public void testAccordion()
    {
        open("/jqueryaccordion");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//h3[contains(@class, 'ui-accordion-header-active')]");
            }
        }.wait("The jQuery UI Accordion has not been initialized", JQueryTestConstants.TIMEOUT);
        
        // active tab must be second
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//h3[contains(@class, 'ui-accordion-header-active')]").contains("Element2");
            }
        }.wait("The second panel should be selected " + getText("//h3[contains(@class, 'ui-accordion-header-active')]"), JQueryTestConstants.TIMEOUT);
       
          //click on first tab
        click("xpath=/html/body/div/div/div/section/div/h3/a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//h3[contains(@class, 'ui-accordion-header-active')]").contains("Element1");
            }
        }.wait("The first panel should be selected", JQueryTestConstants.TIMEOUT);

       
    }
	
	@Test
	public void testDefaultLabelForAccordion(){
		
		open("/jqueryaccordion");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//div[@id='accordion']/h3[4]/a").contains("Block Test");
            }
        }.wait("The fourth accordion does not have the right default value.", JQueryTestConstants.TIMEOUT);
	}
	
	@Test
    public void testAccordionOld()
    {
        open("/jqueryaccordionOld");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//h3[contains(@class, 'ui-accordion-header-active')]");
            }
        }.wait("The jQuery UI Accordion has not been initialized", JQueryTestConstants.TIMEOUT);
        
        // active tab must be second
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//h3[contains(@class, 'ui-accordion-header-active')]").contains("Element2");
            }
        }.wait("The second panel should be selected", JQueryTestConstants.TIMEOUT);
       
          //click on first tab
        click("xpath=/html/body/div/div/div/section/div/h3/a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//h3[contains(@class, 'ui-accordion-header-active')]").contains("Element1");
            }
        }.wait("The first panel should be selected", JQueryTestConstants.TIMEOUT);

       
    }
}
