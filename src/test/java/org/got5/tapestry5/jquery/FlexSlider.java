package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class FlexSlider extends SeleniumTestCase {
	
	@Test
	public void testNavList() {
		open("flexslider");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
            	return getXpathCount("//div[@id='flexslider']/ol/li").equals(3);
            }
        }.wait("The First Slider should have 3 'pages'", JQueryTestConstants.TIMEOUT);
        
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getXpathCount("//div[@id='flexslider_0']/ol/li").equals(12);
            }
        }.wait("The Second Slider should have 12 'pages'", JQueryTestConstants.TIMEOUT);
	}

	@Test
	public void testAutoResfreshedImg() {
		open("flexslider");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
            	return isElementPresent("//div[@id='flexslider_0']/ul/li[contains(@class, 'flex-active-slide')]/img");
            }
        }.wait("We should have an active image", JQueryTestConstants.TIMEOUT);
        
		final String firstImage = getAttribute("//div[@id='flexslider_0']/ul/li[contains(@class, 'flex-active-slide')]/img@src");
		
		new Wait()
        {
            @Override
            public boolean until()
            {
                return !firstImage.equalsIgnoreCase(getAttribute("//div[@id='flexslider_0']/ul/li[contains(@class, 'flex-active-slide')]/img@src"));
            }
        }.wait("The image should changed automatically", 10000l);
	}
}
