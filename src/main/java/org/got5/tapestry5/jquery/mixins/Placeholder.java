package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.Parameter;
import org.got5.tapestry5.jquery.mixins.ui.Widget;
import org.got5.tapestry5.jquery.services.javascript.PlaceholderStack;

/**
 * A jQuery plugin that enables HTML5 placeholder support for legacy browsers.
 * The HTML5 placeholder attribute is awesome, unfortunately only supported by
 * some browsers. This plugin replicates the placeholder behavior for
 * unsupported browsers.
 * 
 * @since 2.6.1
 * @see <a
 *      href="http://webcloud.se/code/jQuery-Placeholder/">http://webcloud.se/code/jQuery-Placeholder/</a>
 * 
 * @tapestrydoc
 */
@Import(stack = PlaceholderStack.STACK_ID)
@MixinAfter
public class Placeholder extends Widget {
	/**
	 * The format you want to use for your input.
	 */
	@Parameter(allowNull = false, required = true, defaultPrefix = BindingConstants.LITERAL)
	private String text;

	void beginRender(MarkupWriter writer) {
		writer.attributes("placeholder", text);
	}
}
