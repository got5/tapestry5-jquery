package org.got5.tapestry5.jquery.test.unit;

import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.utils.JQueryUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JQueryUtilsMergeTest extends Assert{
	
	
	@Test
	public void testPageWithTheme(){
		
		JSONObject val1 = null;
		JSONObject val2 = null;
		
		JQueryUtils.merge(val1, val2);
		assertEquals(val1, null);
		
		
		val1 = new JSONObject("value", "test");
		
		JQueryUtils.merge(val1, val2);
		assertEquals(val1, new JSONObject("value", "test"));
		
		val1 = new JSONObject("value", "test", "value2", "test2");
		val2 = new JSONObject("value", "test4", "value3", "test3");
		
		JQueryUtils.merge(val1, val2);
		assertEquals(val1, new JSONObject("value", "test4", "value2", "test2","value3", "test3"));
	}
	
}
