package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class BindTest extends SeleniumTestCase{
	@Test
    public void testBind()
    {
		open("/test/Bind");
		
		assertSourcePresent("jqbind","\"eventType\" : \"click\"");
		
        
    }

}
