package org.got5.tapestry5.jquery.unit;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.test.PageTester;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ImportJQueryUITest extends Assert{
	
	private PageTester tester;
	
	@BeforeClass
	public void init(){
		
		String appPackage = "org.got5.tapestry5.jquery";
		
		String appName = "app";
		
		tester = new PageTester(appPackage, appName, "src/test/webapp");
	}
	
	@Test
	public void testPageWithTheme(){
		
		Document doc = tester.renderPage("annotations/ImportJQueryUIWithTheme");
		
		assertTrue(doc.getRootElement().getChildMarkup().contains("jquery-ui-1.8.19.custom.css"));
		
	}
	
	@Test
	public void testPageWithoutTheme(){
		
		Document doc = tester.renderPage("annotations/AbstractImportJQueryUI");
		
		assertTrue(doc.getRootElement().getChildMarkup().contains("jquery-ui-1.8.19.custom.css"));
		
	}
	
}
