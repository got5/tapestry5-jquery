package org.got5.tapestry5.jquery.test.pages.docs.mixins;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.got5.tapestry5.jquery.utils.JQueryTabData;
import org.slf4j.Logger;

public class DocsBind {
	@Inject
	private Logger logger;

	public List<JQueryTabData> getListTabData()
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
	    listTabData.add(new JQueryTabData("Documentation","docs"));
	    listTabData.add(new JQueryTabData("Example","example"));
	    return listTabData;
	}
	 
	 
	@OnEvent(value="click")
	void onClick(Integer value) {
		 logger.info("click {}",value);
	}

	@OnEvent(value="slideChange")
	void onSlideChange(Integer value) {
		logger.info("slidechange {}",value);
	}
}
