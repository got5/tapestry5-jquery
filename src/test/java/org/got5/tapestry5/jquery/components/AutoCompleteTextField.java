package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.corelib.components.TextField;

public class AutoCompleteTextField extends TextField{
	
	@Mixin
	private org.got5.tapestry5.jquery.mixins.Autocomplete autocomplete;
}
