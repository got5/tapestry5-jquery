package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ZoneTest extends SeleniumTestCase2{
	
	 @Test
	    public void testZone()
	    {
	        open("/zone");
	        waitForPageToLoad();
	        assertEquals(getText("id=myZone"), "Counting via AJAX : 0");

	        click("//a[@data-update-zone='myZone']");
	        new Wait()
	        {
	            @Override
	            public boolean until()
	            {
	                return isTextPresent("Counting via AJAX : 1");
	            }
	        }.wait("element not found", 5000l);
	        assertEquals(getText("id=myZone"), "Counting via AJAX : 1");
	    }

	    @Test
	    public void testFormZone()
	    {
	        open("/zone");
	        waitForPageToLoad();
	        assertEquals(getText("id=myZone2"), "Dummy value is :");

	        type("id=textfield", "dummy");
	        click("//form[@id='myForm']/input[@type='submit']");

	        new Wait()
	        {
	            @Override
	            public boolean until()
	            {
	                return isTextPresent("Dummy value is : dummy");
	            }
	        }.wait("element not found", 5000l);

	        assertTrue(getText("id=myZone2").contains("Dummy value is : dummy") &&
	        			getText("id=myZone2").contains("I am a Component inside the block which is visible after the zone update"));
	    }

	    @Test(dependsOnMethods="testZone")
	    public void testFormMultiZoneUpdate() 
	    {
	        open("/zone");
	        waitForPageToLoad();
	        assertEquals(getText("id=multiZone1"), "default zone content");
	        assertEquals(getText("id=multiZone2"), "default zone content");
	        
	        click("id=multiSubmit");

	        new Wait()
	        {
	            @Override
	            public boolean until()
	            {
	                return isTextPresent("rendering block");
	            }
	        }.wait("element not found", 5000l);

	        assertEquals(getText("id=multiZone1"), "rendering block-1 after multi zone update 2");
	        assertEquals(getText("id=multiZone2"), "rendering block-2 after multi zone update 2");
	    }
}
