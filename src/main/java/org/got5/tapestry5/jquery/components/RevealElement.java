package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.mixins.RenderInformals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.mixins.Reveal;

/**
 * This component should be used together with the {@link Reveal} mixin.
 *
 * @author criedel
 */
@SupportsInformalParameters
public class RevealElement implements ClientElement {

    @Inject
    private ComponentResources componentResources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Mixin
    private RenderInformals renderInformals;

    /**
     * The element name to render for the reveal element; this defaults to the
     * element actually used in the template, or "div" if no specific element
     * was specified.
     */
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String elementName;

    private String clientId;

    String defaultElementName() {

        return componentResources.getElementName("div");
    }

    void beginRender(MarkupWriter writer) {

        this.clientId = javaScriptSupport.allocateClientId(componentResources);
        writer.element(elementName, "id", clientId);
    }

    void afterRender(MarkupWriter writer) {

        writer.end();
    }

    public String getClientId() {

        return this.clientId;
    }
}
