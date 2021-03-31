package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class SortableTest extends SeleniumTestCase2{
	
	@Test
	public void testSortableMixin(){
		
		open("/Sortable");
		
		new Wait()
	    {
			@Override
	        public boolean until()
	        {
				return getText("//ul[@id='any']/li[1]").equals("Ligne 1") &&
				getText("//ul[@id='any']/li[2]").equals("Ligne 2") &&
				getText("//ul[@id='any']/li[3]").equals("Ligne 3") &&
				getText("//ul[@id='any']/li[4]").equals("Ligne 4") &&
				getText("//ul[@id='any']/li[5]").equals("Ligne 5");
	        }
	    }.wait("The default order is wrong!", JQueryTestConstants.TIMEOUT);

		new Actions(webDriver).dragAndDropBy(
				webDriver.findElement(By.xpath("//ul[@id='any']/li[5]")),
				0, -100
		).perform();

	    new Wait()
	    {
			@Override
	        public boolean until()
	        {
				return getText("//ul[@id='any']/li[1]").equals("Ligne 5") &&
				getText("//ul[@id='any']/li[2]").equals("Ligne 1") &&
				getText("//ul[@id='any']/li[3]").equals("Ligne 2") &&
				getText("//ul[@id='any']/li[4]").equals("Ligne 3") &&
				getText("//ul[@id='any']/li[5]").equals("Ligne 4");
	        }
	    }.wait("The second order is wrong!", JQueryTestConstants.TIMEOUT);

		new Actions(webDriver).dragAndDropBy(
				webDriver.findElement(By.xpath("//ul[@id='any']/li[3]")),
				0, 100
		).perform();
	    
	    new Wait()
	    {
			@Override
	        public boolean until()
	        {
				return getText("//ul[@id='any']/li[1]").equals("Ligne 5") &&
				getText("//ul[@id='any']/li[2]").equals("Ligne 1") &&
				getText("//ul[@id='any']/li[3]").equals("Ligne 3") &&
				getText("//ul[@id='any']/li[4]").equals("Ligne 4") &&
				getText("//ul[@id='any']/li[5]").equals("Ligne 2");
	        }
	    }.wait("The third order is wrong!", JQueryTestConstants.TIMEOUT);
	}
}