package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

public class SelectorTest extends SeleniumTestCase2{
	
	@Test
    public void testSlider()
    {
		open("/Selector");
		
		assertEquals(getText("//div[@id='divId']"),"$('#divId')");		
        
    }

}
