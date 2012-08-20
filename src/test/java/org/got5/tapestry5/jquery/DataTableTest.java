package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class DataTableTest extends SeleniumTestCase{
	
	@Test
	public void testHeaderOverride(){
		open("/DataTables");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getText("//table[@id='datatable']/thead/tr/th[3]/div[@class='DataTables_sort_wrapper']").contains("Header");
			}
		}.wait("The Header is wrong : " + getText("//table[@id='datatable']/thead/tr/th[3]/div[@class='DataTables_sort_wrapper']"), JQueryTestConstants.TIMEOUT);
	}
	
	@Test
	public void testFooterOverride(){
		open("/DataTables");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getText("//table[@id='datatable']/tfoot/tr/th[3]").contains("Footer");
			}
		}.wait("The Footer is wrong : " + getText("//table[@id='datatable']/tfoot/tr/th[3]"), JQueryTestConstants.TIMEOUT);
	}
	
	@Test
	public void testAddColumns(){
		open("/DataTables");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getText("//table[@id='datatable']/thead/tr/th[1]/div[@class='DataTables_sort_wrapper']").contains("Link");
			}
		}.wait("The Columns LINK has not been added!", JQueryTestConstants.TIMEOUT);
	}
	
	@Test
	public void testSortableOverride(){
		open("/DataTables");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return !getAttribute("//table[@id='datatable']/thead/tr/th[3]/div[@class='DataTables_sort_wrapper']/span@class").contains("ui-icon");
			}
		}.wait("The Column should not be sortable.", JQueryTestConstants.TIMEOUT);
	}
	
	@Test
	public void testNumberRows5(){
		open("/DataTables");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getXpathCount("//table[@id='datatable']/tbody/tr").equals(5);
			}
		}.wait("We should have 5 rows", JQueryTestConstants.TIMEOUT);
		
		checkNumberPage(3);
	}
	
	@Test(dependsOnMethods="testNumberRows5")
	public void testNumberRows10(){
		open("/DataTables");
		
		select("//select[@name='datatable_length']","label=10");
		
		focus("//body");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getXpathCount("//table[@id='datatable']/tbody/tr").equals(10);
			}
		}.wait("We should have 5 rows", JQueryTestConstants.TIMEOUT);
		
		checkNumberPage(2);
		
		select("//select[@name='datatable_length']","label=5");
		
		focus("//body");
	}
	
	@Test
	public  void testNavigation(){
		open("/DataTables");

		checkDisable(1);
		click("//div[@id='datatable_paginate']/span/a[contains(@class,'fg-button')][2]");
		checkDisable(2);
		click("//div[@id='datatable_paginate']/span/a[contains(@class,'fg-button')][3]");
		checkDisable(3);
		click("//div[@id='datatable_paginate']/span/a[contains(@class,'fg-button')][1]");
		
	}
	@Test
	public void testCssClass(){
		open("/DataTables");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//table/tbody/tr[1]@class").contains("first");
			}
		}.wait("The first row should have the CSS class 'css'", JQueryTestConstants.TIMEOUT);
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//table/tbody/tr[2]@class").contains("other");
			}
		}.wait("The second row should have the CSS class 'css2'", JQueryTestConstants.TIMEOUT);
	}
	
	@Test
    public void testData()
    {
        open("/DataTables");
        waitForPageToLoad();
        
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
        }.wait("Expected text is missing ["+expectedText2+"]", JQueryTestConstants.TIMEOUT);
        
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
        }.wait("Expected text is missing ["+expectedText+"]", JQueryTestConstants.TIMEOUT);   
                
        
    }
	
	private void checkDisable(final int i) {
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@id='datatable_paginate']/span/a[contains(@class,'fg-button')]["+i+"]@class").contains("ui-state-disabled");
			}
		}.wait("Page " + i + " should be disabled", JQueryTestConstants.TIMEOUT);
	}

	private void checkNumberPage(final int i) {
		new Wait() {
			
			@Override
			public boolean until() {
				return getXpathCount("//div[@id='datatable_paginate']/span/a[contains(@class,'fg-button')]").equals(i);
			}
		}.wait("We should have "+i+" pages", JQueryTestConstants.TIMEOUT);
	}
}
