package org.got5.tapestry5.jquery.utils;

public enum TagLocation {

	BEFORE("before"), AFTER("after");	

	private String value;
	
	TagLocation(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see net.atos.kawwaportal.components.data.TagLocation#getValue()
	 */
	public String getValue() {
		return value;
	}
}