package org.got5.tapestry5.jquery.pages;

import java.util.Date;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.tree.DefaultTreeSelectionModel;
import org.apache.tapestry5.tree.TreeModel;
import org.apache.tapestry5.tree.TreeSelectionModel;
import org.got5.tapestry5.jquery.entities.User;

/**
 * Start page of application stetset.
 */
public class Tree
{
	public Date getCurrentTime() 
	{ 
		return new Date(); 
	}
	
	public TreeModel getModel(){
		return User.createTreeModel();
	}
	   
	public TreeSelectionModel getSelectModel(){
		return new DefaultTreeSelectionModel();
	}
	
	@OnEvent(value=EventConstants.NODE_SELECTED)
	public void nodeSelected(String uuid){
		System.out.println("############## The node with the uid " + uuid + " has been selected");
	}
	
	@OnEvent(value=EventConstants.NODE_UNSELECTED)
	public void nodeUnSelected(String uuid){
		System.out.println("############## The node with the uid " + uuid + " has been selected");
	}
}
