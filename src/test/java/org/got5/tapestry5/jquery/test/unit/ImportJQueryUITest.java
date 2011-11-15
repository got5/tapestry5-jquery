package org.got5.tapestry5.jquery.test.unit;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.test.PageTester;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ImportJQueryUITest extends Assert{
	
	private PageTester tester;
	
	@BeforeClass
	public void init(){
		
		String appPackage = "org.got5.tapestry5.jquery.test";
		
		String appName = "app";
		
		tester = new PageTester(appPackage, appName, "src/test/jquery/webapp");
	}
	
	@Test
	public void testAssetDoesNotExist(){
		
		Document doc = tester.renderPage("test/annotations/ImportJQueryUIAssetDoesNotExist");
		
		assertTrue(doc.getRootElement().getChildMarkup().contains("wrongPath.js file does not exist"));
		
	}
	
	@Test
	public void testPageWithTheme(){
		
		Document doc = tester.renderPage("test/annotations/ImportJQueryUIWithTheme");
		
		assertTrue(doc.getRootElement().getChildMarkup().contains("jquery-ui-1.8.15.custom.css"));
		
	}
	
	@Test
	public void testPageWithoutTheme(){
		
		Document doc = tester.renderPage("test/annotations/AbstractImportJQueryUI");
		
		assertTrue(doc.getRootElement().getChildMarkup().contains("jquery-ui.css"));
		
	}
	
}
