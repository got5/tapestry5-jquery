//
// Copyright 2011 GOT5 (GO Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class CarouselTest extends SeleniumTestCase {

	@BeforeMethod
	public void adjustSpeed() {
		// it seems that integration test are unstable when speed is set to 0
		setSpeed("200");
	}

	@Test
	public void testCarousel() {
		open("/carouselpage");
		waitForPageToLoad();
		//Content test
		assertEquals(getText("css=#zoneContainer span"), "Click on the big white flower of the complex carousel:", "The page does not seem to be fully loaded");
		//test of an eventlink inside a carousel
		assertTrue("".equals(getText("id=flowerZone")),"The zone is not empty before the ajax call");
		click("//*[@id='carouselitem_0']/img");
		assertEquals(getText("id=flowerZone"),"You just clicked on the big white flower !", "The eventlink did not work, the zone did not load correctly");
		
		//Carousel control test
		final int initPosition = (Integer) getElementPositionLeft("css=.jcarousel-list");
		click("css=.jcarousel-next");
		new Wait()
        {
            @Override
            public boolean until()
            {
            	int newPosition = (Integer) getElementPositionLeft("css=.jcarousel-list");
            	return Math.abs(initPosition - newPosition)==255;
            }
        }.wait("The carousel did not slide correctly", JQueryTestConstants.TIMEOUT);
		
		
	}
}
