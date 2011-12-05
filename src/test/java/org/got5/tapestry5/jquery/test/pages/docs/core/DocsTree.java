package org.got5.tapestry5.jquery.test.pages.docs.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.tree.DefaultTreeSelectionModel;
import org.apache.tapestry5.tree.TreeModel;
import org.apache.tapestry5.tree.TreeSelectionModel;
import org.got5.tapestry5.jquery.test.entities.User;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

/**
 * Start page of application stetset.
 */
public class DocsTree
{
	public List<JQueryTabData> getListTabData()
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
        listTabData.add(new JQueryTabData("Example","example"));
        return listTabData;
    }
	
public TreeModel getModel(){
	return User.createTreeModel();
}
	   
public TreeSelectionModel getSelectModel(){
	return new DefaultTreeSelectionModel();
}
	
@OnEvent(value=EventConstants.NODE_SELECTED)
public void nodeSelected(String uuid){
	System.out.println("The node with the uid " + uuid + " has been selected");
}
	
@OnEvent(value=EventConstants.NODE_UNSELECTED)
public void nodeUnSelected(String uuid){
	System.out.println("The node with the uid " + uuid + " has been selected");
}
}
