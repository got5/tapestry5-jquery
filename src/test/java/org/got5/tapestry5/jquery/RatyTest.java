package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class RatyTest extends SeleniumTestCase2{
	
	@Test
	public void testReadOnly(){
		open("/Raty");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//div[@id='anyRaty'][contains(@title, 'Really bad')]");
			}
		}.wait("The Read value is wrong", JQueryTestConstants.TIMEOUT);
		
		checkImgs(1, "//div[@id='anyRaty']/img");
	}
	
	
	@Test
	public void testReadWrite(){
		open("/Raty");
		
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent("//form//img");
			}
		}.wait("The Read/Write stars are missing", JQueryTestConstants.TIMEOUT);
		
		mouseOver("//form//img[@alt='2']");
		click("//form//img[@alt='2']");
		
		checkImgs(2, "//form//img");
		
		click("//input[@type='submit']");
		
		//checkImgs(2, "//form//img");
	}
	
	private void checkImgs(final int index, final String selector){
		new Wait() {
			
			@Override
			public boolean until() {
				return isElementPresent(getPath(index, selector, 1)) &&
						isElementPresent(getPath(index, selector, 2)) &&
						isElementPresent(getPath(index, selector, 3)) &&
						isElementPresent(getPath(index, selector, 4)) &&
						isElementPresent(getPath(index, selector, 5));
			}

			private String getPath(int index, String selector, int i) {
				if(i<=index)
					return selector + "[@alt='"+i+"'][contains(@src, 'star-on.png')]";
				
				return selector + "[@alt='"+i+"'][contains(@src, 'star-off.png')]";
			}
		}.wait("you should have " + index +" stars selected.", JQueryTestConstants.TIMEOUT);
	}
}
