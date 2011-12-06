package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ZoneTest extends SeleniumTestCase{
	
	 @Test
	    public void testZone()
	    {
	        open("/test/zone");
	        waitForPageToLoad();
	        assertEquals(getText("identifier=myZone"), "Counting via AJAX : 0");

	        click("identifier=myActionLink");
	        new Wait()
	        {
	            @Override
	            public boolean until()
	            {
	                return isTextPresent("Counting via AJAX : 1");
	            }
	        }.wait("element not found", 5000l);
	        assertEquals(getText("identifier=myZone"), "Counting via AJAX : 1");
	    }

	    @Test
	    public void testFormZone()
	    {
	        open("/test/zone");
	        waitForPageToLoad();
	        assertEquals(getText("identifier=myZone2"), "Dummy value is :");

	        type("identifier=textfield", "dummy");
	        click("identifier=submit");

	        new Wait()
	        {
	            @Override
	            public boolean until()
	            {
	                return isTextPresent("Dummy value is : dummy");
	            }
	        }.wait("element not found", 5000l);

	        assertTrue(getText("identifier=myZone2").contains("Dummy value is : dummy") && 
	        			getText("identifier=myZone2").contains("I am a Component inside the block which is visible after the zone update"));
	    }

	    @Test
	    public void testFormMultiZoneUpdate() 
	    {
	        open("/test/zone");
	        waitForPageToLoad();
	        assertEquals(getText("identifier=multiZone1"), "default zone content");
	        assertEquals(getText("identifier=multiZone2"), "default zone content");
	        
	        click("identifier=multiSubmit");

	        new Wait()
	        {
	            @Override
	            public boolean until()
	            {
	                return isTextPresent("rendering block");
	            }
	        }.wait("element not found", 5000l);

	        assertEquals(getText("identifier=multiZone1"), "rendering block-1 after multi zone update");
	        assertEquals(getText("identifier=multiZone2"), "rendering block-2 after multi zone update");
	    }
}
