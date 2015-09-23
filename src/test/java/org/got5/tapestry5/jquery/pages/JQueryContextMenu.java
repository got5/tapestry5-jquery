package org.got5.tapestry5.jquery.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.got5.tapestry5.jquery.data.ContextMenuItem;

public class JQueryContextMenu {

	@Property @Persist
	private int count;

	// NOTE: make sure this is explicitly the tapestry zone, not the Zone page in the current package
	@Component
	org.apache.tapestry5.corelib.components.Zone contextMenuZone;

	public List<ContextMenuItem> getContextMenuItems() {
		List<ContextMenuItem> items = new ArrayList<ContextMenuItem>();
		items.add(new ContextMenuItem("Editor", "edit_event", "edit"));
		items.add(ContextMenuItem.getSeparator());
		items.add(new ContextMenuItem("Copy", "copy_event", "copy"));
		items.add(new ContextMenuItem("Cut", "cut_event", "cut"));
		items.add(new ContextMenuItem("Paste", "paste_event", "paste"));
		items.add(ContextMenuItem.getSeparator());
		items.add(new ContextMenuItem("Delete", "delete_event", "delete"));
		items.add(new ContextMenuItem("Quit", "quit_event", "quit"));
		return items;
	}

	public List<ContextMenuItem> getCounterMenuItems() {
		List<ContextMenuItem> items = new ArrayList<ContextMenuItem>();
		items.add(new ContextMenuItem("Increment Counter", "inc_event"));
		items.add(new ContextMenuItem("Decrement Counter", "dec_event"));
		items.add(new ContextMenuItem("Reset Counter", "reset_event"));
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


	/*
	 * Event handlers for the zone-update events
	 */

	@OnEvent(value="inc_event")
	public Zone onIncEvent() {
		count++;
		return contextMenuZone;
	}

	@OnEvent(value="dec_event")
	public Zone onDecEvent() {
		count--;
		return contextMenuZone;
	}

	@OnEvent(value="reset_event")
	public Zone onResetEvent() {
		count = 0;
		return contextMenuZone;
	}
}
