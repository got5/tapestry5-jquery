package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class JScrollPaneTest extends SeleniumTestCase {

	@Test
	public void testVerticalOnly() {
		open("jscrollpane");

		new Wait() {

			@Override
			public boolean until() {
				return isElementPresent("//div[@id='any']/div/div[@class='jspVerticalBar']")
						&& !isElementPresent("//div[@id='any']/div/div[@class='jspHorizontalBar']");
			}
		}.wait("We should have a Vertical Bar", JQueryTestConstants.TIMEOUT);
	}

	@Test
	public void testHorizontalOnly() {
		open("jscrollpane");

		new Wait() {

			@Override
			public boolean until() {
				return !isElementPresent("//div[@id='any_0']/div/div[@class='jspVerticalBar']")
						&& isElementPresent("//div[@id='any_0']/div/div[@class='jspHorizontalBar']");
			}
		}.wait("We should have a Horizontal Bar", JQueryTestConstants.TIMEOUT);
	}

	@Test
	public void testBoth() {
		open("jscrollpane");

		new Wait() {

			@Override
			public boolean until() {
				return isElementPresent("//div[@id='any_1']/div/div[@class='jspVerticalBar']")
						&& isElementPresent("//div[@id='any_1']/div/div[@class='jspHorizontalBar']");
			}
		}.wait("We should have a Vertical and Horizonal Bars",
				JQueryTestConstants.TIMEOUT);
	}
}
