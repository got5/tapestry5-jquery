package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class DraggableTest extends SeleniumTestCase{

	@Test 
	public void dragItem1(){
		open("/draggable");
		dragAndDropToObject("//div[@id='draggableItem1']", "//div[@id='dropzone']");
		testDroppableZone("contexteItem1");
	}
	
	@Test 
	public void dragItem2(){
		open("/draggable");
		dragAndDropToObject("//div[@id='draggableItem2']", "//div[@id='dropzone']");
		testDroppableZone("contexteItem2");
	}
	
	private void testDroppableZone(final String value){
		new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("//div[@id='dropzone']").contains(value);
            }
        }.wait("The Droppable Zone does not contain the right value", JQueryTestConstants.TIMEOUT);
	}
}
