package org.got5.tapestry5.jquery.mixins;


import org.apache.tapestry5.annotations.Import;
import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.mixins.ui.Widget;

/**
 * This mixin allows you to display a tooltip for all your inputs. 
 * It use the title attribute of your element.
 * 
 * @since 2.1.1
 * @see <a href="http://access.aol.com/csun2011">http://access.aol.com/csun2011</a>
 *
 * @tapestrydoc
 */
@ImportJQueryUI({ "jquery.ui.widget", "jquery.ui.core" })
@Import(library = { "${assets.path}/mixins/tooltip/jquery.ui.tooltip.js" }, 
		stylesheet = { "${assets.path}/mixins/tooltip/jquery.ui.tooltip.css" })
public class Tooltip extends Widget{

}
