package org.got5.tapestry5.jquery.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.got5.tapestry5.jquery.data.ContextMenuItem;

public class JQueryContextMenu {
	
	public List<ContextMenuItem> getContextMenuItems() {
		List<ContextMenuItem> items = new ArrayList<ContextMenuItem>();
		items.add(new ContextMenuItem("Editer", "edit_event", "edit"));
		items.add(ContextMenuItem.getSeparator());
		items.add(new ContextMenuItem("Copy", "copy_event", "copy"));
		items.add(new ContextMenuItem("Cut", "cut_event", "cut"));
		items.add(new ContextMenuItem("Paste", "paste_event", "paste"));
		items.add(ContextMenuItem.getSeparator());
		items.add(new ContextMenuItem("Delete", "delete_event", "delete"));
		items.add(new ContextMenuItem("Quit", "quit_event", "quit"));
		return items;
	}
	
	@OnEvent(value="edit_event")
	public void onEditEvent() {
		System.out.println("test edit_event");
	}
	
	@OnEvent(value="copy_event")
	public void onCopyEvent() {
		System.out.println("test copy_event");
	}
	
	@OnEvent(value="cut_event")
	public void onCutEvent() {
		System.out.println("test cut_event");
	}
}
