package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.annotations.OnEvent;
import org.got5.tapestry5.jquery.JQueryEventConstants;


public class Sortable {
	
	@OnEvent(JQueryEventConstants.SORTABLE)
	public void onSort(String context){
		
			System.out.println("######## " + context);
		
	}

}
