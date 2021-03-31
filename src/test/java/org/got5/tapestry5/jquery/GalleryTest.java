package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class GalleryTest extends SeleniumTestCase2 {

    @BeforeMethod
    public void adjustSpeed() {

        // it seems that integration test are unstable when speed is set to 0
        setSpeed("200");
    }

    @Test
    public void testGallery() {

        open("/galleryPage");
        waitForPageToLoad();
        // Content test
        click("id=image1");
        new Wait() {

            @Override
            public boolean until() {

                return isVisible("id=colorbox");
            }
        }.wait("The gallery is not open :-(", 3000);

        click("id=cboxClose");
        new Wait() {

            @Override
            public boolean until() {

                return !isVisible("id=colorbox");
            }
        }.wait("The gallery is still open, expected it to close :-(", 3000);

    }
}
