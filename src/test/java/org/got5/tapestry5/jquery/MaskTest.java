package org.got5.tapestry5.jquery;

import org.got5.tapestry5.jquery.test.SeleniumTestCase2;
import org.testng.annotations.Test;

public class MaskTest extends SeleniumTestCase2{
	
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
