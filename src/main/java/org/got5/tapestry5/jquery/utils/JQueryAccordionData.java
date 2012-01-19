package org.got5.tapestry5.jquery.utils;

/**
 * @deprecated will be deleted to Tapestry5-jQuery 4.0.0
 */
public class JQueryAccordionData {
	
	private String title;
	private String blockName;
	
	public JQueryAccordionData(String title, String blockName) {
		super();
		this.title = title;
		this.blockName = blockName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	
	
}
