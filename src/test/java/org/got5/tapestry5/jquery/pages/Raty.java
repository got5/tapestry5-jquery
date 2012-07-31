package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.TapestryInternalUtils;

public class Raty {
	
	@Persist
	@Property 
	private Integer value; 
	
	public SelectModel getModel(){
		return TapestryInternalUtils.toSelectModel("1=1 - Really bad,2=2 - Bad,3=3 - Regular,4=4 - Good,5=5 - Really good");
	}
	
	public void onSubmit(){
		System.out.println("########## " + value);
	}
}
