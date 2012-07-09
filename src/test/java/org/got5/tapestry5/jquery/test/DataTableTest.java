package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class DataTableTest extends SeleniumTestCase{
	@Test
    public void testDataTableAjaxMode()
    {
        open("/test/datatables");
        waitForPageToLoad();
        //click on first page
        click("datatable_first");
        final String expectedText = "Clinton";
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getText("//table[@id='datatable']").contains(expectedText);
            }
        }.wait("Expected text is missing ["+expectedText+"]", 5000l);   
                
        //click on next page
        click("datatable_next");
        final String expectedText2 = "Pascal";
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getText("//table[@id='datatable']").contains(expectedText2);
            }
        }.wait("Expected text is missing ["+expectedText2+"]", 5000l);       
    }
}
