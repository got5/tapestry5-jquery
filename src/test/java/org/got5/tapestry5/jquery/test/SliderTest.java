package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class SliderTest extends SeleniumTestCase {
	
	@Test
    public void testSlider()
    {
		open("/test/Slider");
		
		assertAttribute("//div[@id='any']@class","ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all");
		
        
    }

}
