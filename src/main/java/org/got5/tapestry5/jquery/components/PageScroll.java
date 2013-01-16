package org.got5.tapestry5.jquery.components;


import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Loop;
import org.apache.tapestry5.internal.services.ArrayEventContext;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;

/**
 * Infinite page scroll component based on
 * <a href='http://contextllc.com/tools/jQuery-infinite-scroll-live-scroll'>
 * jQuery onScrollBeyond()
 * </a>
 * @tapestrydoc
 */
@Events(JQueryEventConstants.NEXT_PAGE)
@Import(library =
    {
        "${assets.path}/components/pagescroll/jquery.scrollExtend.min.js",
        "${assets.path}/components/pagescroll/PageScroll.js"
    },
    stylesheet = "${assets.path}/components/pagescroll/PageScroll.css")
public class PageScroll implements ClientElement {

    /* Embedded loop component */
    @Component(publishParameters = "encoder, formState, element, index, empty")
    private Loop<?> loop;

    /**
     * Used to store the current object being rendered(for the current row).
     * It can be used in the body to know what should be rendered.
     */
    @Parameter
    @Property
    private Object row;

    /**
     * The id used to generate a page-unique client-side identifier for the component.
     * If a component renders multiple times, a suffix will be appended to the to id
     * to ensure uniqueness. The unique id value may be accessed via the clientId property.
     */
    @Parameter(value = "prop:componentResources.id",
        defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    /**
     * Current page number
     */
    @Parameter(required = true)
    private int pageNumber;

    /**
     * Zone to be updated each time page is scrolled.
     */
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String zone;

    /**
     * Scroller to use
     */
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String scroller;

    /**
     * Any additional parameter options to be specified. See
     * <a href='http://contextllc.com/tools/jQuery-infinite-scroll-live-scroll'>here</a>
     * for more details.
     */
    @Parameter
    private JSONObject params;

    /**
     * Context to be passed on page scroll.
     */
    @Parameter(value = "literal:[]")
    private Object[] context;

    private String assignedClientId;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private Block nextPageBlock;

    @Inject
    private TypeCoercer typeCoercer;

    @Inject
    private Request request;

    private EventContext eventContext;

    @BeginRender
    void initialize() {
        assignedClientId = javaScriptSupport.allocateClientId(clientId);
        eventContext = new ArrayEventContext(typeCoercer, context);
    }

    @AfterRender
    void addJavaScript() {
        JSONObject specs = new JSONObject()
            .put("scroller", scroller)
            .put("scrollURI", getScrollURI())
            .put("zoneId", zone)
            .put("params", params);

        javaScriptSupport.addInitializerCall("PageScroll", specs);
    }

    @OnEvent("scroll")
    Object scroll(EventContext context, @RequestParameter("pageNumber") int index) {
        this.pageNumber = index;
        this.eventContext = context;
        return nextPageBlock;
    }

    public List<?> getNextPage() {
        CaptureResultCallback<List<Object>> resultCallback = new CaptureResultCallback<List<Object>>();
        resources.triggerContextEvent(JQueryEventConstants.NEXT_PAGE, eventContext, resultCallback);

        List<?> result = resultCallback.getResult();
        result = (result == null ? new ArrayList<Object>() : result);

        return result;
    }

    public String getClientId() {
        return assignedClientId;
    }

    public String getScrollURI() {
        return resources.createEventLink("scroll", context).toAbsoluteURI();
    }

}
