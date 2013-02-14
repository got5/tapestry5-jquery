package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ValidationTest extends SeleniumTestCase{
	
	@Test
    public void testValidation()
    {
        open("/validation");
        waitForPageToLoad();

        assertValidationWorking("field", "a", true);
        assertValidationWorking("field", "abcdefghijklmnopq", true);
        assertValidationWorking("field", "abcd", false);

        assertValidationWorking("field2", "0", true);
        assertValidationWorking("field2", "10", true);
        assertValidationWorking("field2", "3", false);

    }

    protected void assertValidationWorking(final String fieldId, String value, final boolean validationVisible)
    {
        focus("//input[@type='text'][@id='"+fieldId+"']");
        type("//input[@type='text'][@id='"+fieldId+"']", value);
        click("//input[@type='submit']");

		final String locator = "//p[contains(@data-error-block-for, '"+fieldId+"')]";
	   new Wait()
	   {
		   @Override
	       public boolean until(){
			   
			   return validationVisible ? blockShouldBeVisible(locator) : blockShouldNotBeVisible(locator);
			  
		   }
	   }.wait("The Element " + fieldId + " not found", 5000l);
        
    }
    
    private boolean blockShouldBeVisible(String locator){
    	return isElementPresent(locator) && !getAttribute(locator + "@style").contains("none");
    }
    
    private boolean blockShouldNotBeVisible(String locator){
    	return !blockShouldBeVisible(locator);
    }
}
