package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;

/**
 * A jQuery plugin that enables HTML5 placeholder support for legacy browsers.
 * The HTML5 placeholder attribute is awesome, unfortunately only supported by some browsers. 
 * This plugin replicates the placeholder behavior for unsupported browsers.
 * 
 * @since 2.6.1
 * @see <a href="http://webcloud.se/code/jQuery-Placeholder/">http://webcloud.se/code/jQuery-Placeholder/</a>
 */
@Import(library = "${assets.path}/mixins/placeholder/jquery.placeholder.min.js")
@MixinAfter
public class Placeholder {

    @Inject
    @Symbol(JQuerySymbolConstants.JQUERY_ALIAS)
    private String jqueryAlias;

    /**
     * The format you want to use for your input.
     */
    @Parameter(allowNull = false, required = true)
    private String text;

    /**
     * The field component to which this mixin is attached.
     */
    @InjectContainer
    private ClientElement element;

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    void beginRender(MarkupWriter writer) {
        writer.attributes("placeholder", text);
    }

    /**
     * Mixin afterRender phrase occurs after the component itself.
     * 
     * @param writer
     */
    void afterRender(MarkupWriter writer) {
        javaScriptSupport.addScript("%s('#%s').placeholder();", jqueryAlias, element.getClientId());
    }
}
