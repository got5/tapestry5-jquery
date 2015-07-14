package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class InPlaceEditorTest extends SeleniumTestCase{

	@Test
	public void testInPlaceEditor(){
		
		open("/DocsInPlaceEditor");
		waitForPageToLoad();

        click("//*[@id='inplaceeditor']");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//*[@id='inplaceeditor']/form");
            }
        }.wait("The input is not visible", JQueryTestConstants.TIMEOUT);
        
        type("//*[@id='inplaceeditor']/form/input", "François");
        
        click("//*[@id='inplaceeditor']/form/button[@type='submit']");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//*[@id='inplaceeditor']").contains("François");
            }
        }.wait("The value has not been updated", JQueryTestConstants.TIMEOUT);
	}
}
