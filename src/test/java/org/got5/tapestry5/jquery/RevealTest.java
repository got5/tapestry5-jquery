package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class RevealTest extends SeleniumTestCase {

    @Test
    public void testRevealMixin() {

        open("/reveal");
        waitForPageToLoad();

        assertFalse(isVisible("//div[@id='myDiv']"));

        click("//a[@name='monLink2']");

        testModal("myDiv");
    }

    @Test
    public void testRevealMixinWithSelector() {

        open("/reveal");
        waitForPageToLoad();

        assertFalse(isVisible("//div[@id='myDiv']"));

        click("//a[@name='monLink']");

        testModal("myDiv");
    }

    @Test
    public void testRevealMixinWithRevealElement() {

        open("/reveal");
        waitForPageToLoad();

        assertFalse(isVisible("//div[@id='myDiv']"));

        click("//a[@name='myLink3']");

        testModal("revealThis");
    }

    private void testModal(final String modalId) {

        final String modalSelector = String.format("//div[@id='%s']", modalId);

        new Wait() {

            @Override
            public boolean until() {

                return isVisible(modalSelector);
            }
        }.wait("The reveal window is not visible", JQueryTestConstants.TIMEOUT);

        click("//div[@class='reveal-modal-bg']");

        new Wait() {

            @Override
            public boolean until() {

                return !isVisible(modalSelector);
            }
        }.wait("The reveal window visible", JQueryTestConstants.TIMEOUT);
    }
}
