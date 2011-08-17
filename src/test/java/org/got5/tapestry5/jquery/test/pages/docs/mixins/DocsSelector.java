package org.got5.tapestry5.jquery.test.pages.docs.mixins;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

public class DocsSelector {
	
	public List<JQueryTabData> getListTabData()
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
	    listTabData.add(new JQueryTabData("Documentation","docs"));
	    listTabData.add(new JQueryTabData("Example","example"));
	    return listTabData;
	}
	 
	 @OnEvent("slideChanged")
	 void onSlideChanges(Integer value) {
		 
	 }
}
