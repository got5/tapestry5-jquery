package org.got5.tapestry5.jquery.utils;

public class JQueryTabData {
	
	private String title;
	private String blockName;
	
	public JQueryTabData(String title, String blockName) {
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