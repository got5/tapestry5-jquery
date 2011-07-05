package org.got5.tapestry5.jquery.test.pages.test;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ProgressiveDisplay {

	@Inject
	private Block testBlock;
	
	@OnEvent(EventConstants.PROGRESSIVE_DISPLAY)
	public Object returnBlock(){
		return testBlock;
	}
}
