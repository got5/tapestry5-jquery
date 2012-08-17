package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class DataTableAjaxTest extends SeleniumTestCase{
	
	@Test
	public void testHeaderOverride(){
		open("/DataTablesAjax");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getText("//table[@id='datatableAjax']/thead/tr/th[2]/div[@class='DataTables_sort_wrapper']").contains("Header");
			}
		}.wait("The Header is wrong : " + getText("//table[@id='datatableAjax']/thead/tr/th[2]/div[@class='DataTables_sort_wrapper']"), JQueryTestConstants.TIMEOUT);
	}
	
	@Test
	public void testFooterOverride(){
		open("/DataTablesAjax");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getText("//table[@id='datatableAjax']/tfoot/tr/th[2]").contains("Footer");
			}
		}.wait("The Footer is wrong : " + getText("//table[@id='datatableAjax']/tfoot/tr/th[2]"), JQueryTestConstants.TIMEOUT);
	}
	
	@Test
	public void testSortableOverride(){
		open("/DataTablesAjax");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return !getAttribute("//table[@id='datatableAjax']/thead/tr/th[2]/div[@class='DataTables_sort_wrapper']/span@class").contains("ui-icon");
			}
		}.wait("The Column should not be sortable.", JQueryTestConstants.TIMEOUT);
	}
	
	@Test
	public void testNumberRows5(){
		open("/DataTablesAjax");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(5);
			}
		}.wait("We should have 5 rows", JQueryTestConstants.TIMEOUT);
		
		checkNumberPage(3);
	}
	
	@Test(dependsOnMethods="testNumberRows5")
	public void testNumberRows10(){
		open("/DataTablesAjax");
		
		select("//select[@name='datatableAjax_length']","label=10");
		
		focus("//body");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(10);
			}
		}.wait("We should have 10 rows", JQueryTestConstants.TIMEOUT);
		
		checkNumberPage(2);
		
		select("//select[@name='datatableAjax_length']","label=5");
		
		focus("//body");
	}
	
	@Test(dependsOnMethods="testNumberRows10")
	public  void testFilter(){
		open("/DataTablesAjax");
		type("//div[@id='datatableAjax_filter']/label/input[@type='text']", "Ka");
		keyUp("//div[@id='datatableAjax_filter']/label/input[@type='text']", "f");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(1);
			}
		}.wait("The filter does not work", JQueryTestConstants.TIMEOUT);	
		//reset filter
		type("//div[@id='datatableAjax_filter']/label/input[@type='text']", "");
		keyUp("//div[@id='datatableAjax_filter']/label/input[@type='text']", " ");
		new Wait() {
			
			@Override
			public boolean until() {
				return !getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(1);
			}
		}.wait("The reset filter does not work", JQueryTestConstants.TIMEOUT);
	}
	
	@Test
    public void testData()
    {
        open("/DataTablesAjax");
        waitForPageToLoad();
        
        //click on first page
        click("datatableAjax_first");
        final String expectedText = "Clinton";
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getText("//table[@id='datatableAjax']").contains(expectedText);
            }
        }.wait("Expected text is missing ["+expectedText+"]", JQueryTestConstants.TIMEOUT);   
                
        //click on next page
        click("datatableAjax_next");
        final String expectedText2 = "Pascal";
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getText("//table[@id='datatableAjax']").contains(expectedText2);
            }
        }.wait("Expected text is missing ["+expectedText2+"]", JQueryTestConstants.TIMEOUT);       
    }
	
	private void checkDisable(final int i) {
		new Wait() {
			
			@Override
			public boolean until() {
				return getAttribute("//div[@id='datatableAjax_paginate']/span/a[contains(@class,'fg-button')]["+i+"]@class").contains("ui-state-disabled");
			}
		}.wait("Page " + i + " should be disabled", JQueryTestConstants.TIMEOUT);
	}

	private void checkNumberPage(final int i) {
		new Wait() {
			
			@Override
			public boolean until() {
				return getXpathCount("//div[@id='datatableAjax_paginate']/span/a[contains(@class,'fg-button')]").equals(i);
			}
		}.wait("We should have "+i+" pages, instead we have " + getXpathCount("//div[@id='datatableAjax_paginate']/span/span[contains(@class,'fg-button')]"), JQueryTestConstants.TIMEOUT);
	}
	
	
}
