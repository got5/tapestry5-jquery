package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;


public class DraggableTest extends SeleniumTestCase2 {

    @Test
    public void dragItem1() {

        open("/draggable");
        waitForPageToLoad();
        testDroppableZone("//div[@id='draggableItem1']", "contexteItem1");
    }

    @Test
    public void dragItem2() {

        open("/draggable");
        waitForPageToLoad();
        testDroppableZone("//div[@id='draggableItem2']", "contexteItem2");
    }

    private void testDroppableZone(final String item, final String value) {

        new Wait() {

            @Override
            public boolean until() {

                /*
                 * Selenium needs at least two drag'n'drops after page load for
                 * some reason to trigger the drop event. I couldn't manually
                 * reproduce this behaviour.
                 */
                dragAndDropToObject(item, "//div[@id='dropzone']");
                return getText("//div[@id='dropzone']").contains(value);
            }
        }.wait("The Droppable Zone does not contain the right value", JQueryTestConstants.TIMEOUT);
    }
}