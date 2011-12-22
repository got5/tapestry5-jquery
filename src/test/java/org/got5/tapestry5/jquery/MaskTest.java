package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class MaskTest extends SeleniumTestCase{
	
	/**
     * Test Method for Mask mixin
     */
    @Test
    public void testMask(){
    	
    	open("/mask");
    	
    	click("//a[@id='myTestLink']");
    	
    	assertTrue(getValue("//input[@id='monTextField']").contains("1"));
    	
    	click("//a[@id='myTestLink2']");
    	
    	assertFalse(getValue("//input[@id='monTextField']").contains("a"));
    	
    }
}
