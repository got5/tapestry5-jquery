package org.got5.tapestry5.jquery.data;

/**
 * ContextMenu item. Used to fill the parameter items of the ContextMenu object.
 */
public class ContextMenuItem {
	
	/**
	 * Returns a menu separator.
	 */
	public static ContextMenuItem getSeparator() {
		return new ContextMenuItem(true);
	}
	
	/**
	 * Item label displayed in the menu. Mandatory. 
	 */
	public String label;
	
	/**
	 * Value of the event dispatched by the menu item. Mandatory.
	 * 
	 * For example, if event=copy, you will write the following handler to catch the click
	 * event on the menu item:
	 * 
	 * @OnEvent(value="copy")
	 * private void copyData() {
	 *   ...
	 * }
	 */
	public String event;
	
	/**
	 * Defines the css class called to get the item icon. Optionnal. 
	 * 
	 * For example, if icon=cutData, following css class will be used:
	 * 
	 * .context-menu-item.icon-cutData { background-image: url(images/cutData.png); }
	 * 
	 * Several icons already exist in jquery.contextMenu.css:
	 * edit, cut, copy, paste, delete, add, quit
	 */
	public String icon;
	
	/**
	 * Defines if the menu item is a separator or not.
	 */
	private boolean isSeparator = false;
	
	public boolean getIsSeparator() {
		return isSeparator;
	}
	
	public ContextMenuItem(String label, String event, String icon) {
		this(label, event);
		this.icon = icon;
	}
	
	public ContextMenuItem(String label, String event) {
		this.label = label;
		this.event = event;
	}
	
	private ContextMenuItem(boolean isSeparator) {
		this.isSeparator = isSeparator;
	}
}
