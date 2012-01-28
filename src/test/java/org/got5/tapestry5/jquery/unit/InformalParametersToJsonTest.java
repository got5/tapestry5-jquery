package org.got5.tapestry5.jquery.unit;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.test.PageTester;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InformalParametersToJsonTest extends Assert{
private PageTester tester;
	
	@BeforeClass
	public void init(){
		
		String appPackage = "org.got5.tapestry5.jquery";
		
		String appName = "app";
		
		tester = new PageTester(appPackage, appName, "src/test/webapp");
	}
	
	@Test
	public void testPageWithTheme(){
		
		Document doc = tester.renderPage("InformalParametersToJson");
		
		assertTrue(doc.getRootElement().getChildMarkup().contains("color"));
		assertTrue(doc.getRootElement().getChildMarkup().contains("red"));
		assertTrue(doc.getRootElement().getChildMarkup().contains("size"));
		assertTrue(doc.getRootElement().getChildMarkup().contains("10"));
		
	}
}
