package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.services.javascript.GalleryStack;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * Gallery component based on http://colorpowered.com/colorbox/
 *
 * @author criedel
 * @since 2.6.1
 * 
 * @tapestrydoc
 */
@Import(stack = GalleryStack.STACK_ID)
public class Gallery extends AbstractExtendableComponent {

    @Inject
    private JavaScriptSupport javaScriptSupport;

    /**
     * A CSS selector that defines the desired elements for the gallery. The
     * following selector would include all links that have the rel-attribute
     * set to 'gallery': <br />
     * <code>
     *  t:selector="a[rel^=gallery]"
     * </code>
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, allowNull = false)
    private String selector;

    /**
     * Additional parameters (please refer to the colorbox documentation)
     */
    @Parameter
    private JSONObject params;

    @SetupRender
    void setup() {

        setDefaultMethod("gallery");
    }

    @AfterRender
    void afterRender() {

        if (params == null)
            params = new JSONObject();

        final JSONObject options = new JSONObject();
        options.put("selector", selector);

        JQueryUtils.merge(options, params);

        javaScriptSupport.addInitializerCall(getInitMethod(), options);
    }
}