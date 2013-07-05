package org.got5.tapestry5.jquery.components;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.PartialMarkupRenderer;
import org.apache.tapestry5.services.PartialMarkupRendererFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;
import org.got5.tapestry5.jquery.JQueryComponentConstants;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.base.AbstractExtendableComponent;
import org.got5.tapestry5.jquery.services.AjaxUploadDecoder;
import org.got5.tapestry5.jquery.services.javascript.AjaxUploadStack;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * File-Upload component based on Tapestry-Upload and
 * https://github.com/valums/file-uploader
 *
 * <p>
 * The AjaxUpload will trigger the event
 * {@link JQueryEventConstants#AJAX_UPLOAD} after a file has been uploaded to
 * the temporary directory.
 * </p>
 *
 * @author criedel
 * 
 * @tapestrydoc
 */
@Events( { JQueryEventConstants.AJAX_UPLOAD, JQueryEventConstants.NON_XHR_UPLOAD } )
@Import(stack = AjaxUploadStack.STACK_ID)
public class AjaxUpload extends AbstractExtendableComponent {

    /**
     * Put this as a key of your JSON response in case of NON_XHR_UPLOAD events.
     * A JSON response of { UPDATE_ZONE_CALLBACK : { url : /your_event_callback_url/, params : /any_custom_params/ } }
     */
    public static final String UPDATE_ZONE_CALLBACK = "updateZone";

    private static final String[] UNITS = new String[] {"K", "M", "G"};

    /**
     * (optional, default is false)
     * Adds the 'multiple' attribute to the input element.
     */
    @Parameter(value = "false")
    private boolean multiple;

    /**
     * (optional, all files are allowed by default)
     * Restrict allowed file extensions.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String allowedExtensions;

    /**
     * (optional, defaults to 0 = no limit)
     *
     * The maximum size of one single file in bytes. If the number has a
     * trailing K, M or G the limit will be calculated accordingly.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "0")
    private String sizeLimit;

    /**
     * (optional, defaults to 3)
     * Limits the amount of parallel uploads.
     */
    @Parameter(value = "3")
    private int maxConnections;

    /**
     * Additional parameters (please refer to valum's file uploader documentation)
     */
    @Parameter
    private JSONObject params;

    @Parameter
    private Object[] context;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private MultipartDecoder multipartDecoder;

    @Inject
    private AjaxUploadDecoder ajaxDecoder;

    @Inject
    private Request request;

    @Inject
    private Messages messages;

    @InjectComponent
    private Dialog uploadErrorMesages;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @SetupRender
    void setup() {

        setDefaultMethod("uploadable");
    }

    void afterRender() {

        if (params == null)
            params = new JSONObject();

        final JSONObject uploadMessages = new JSONObject()
                .put("typeError", messages.get("typeError"))
                .put("sizeError", messages.get("sizeError"))
                .put("minSizeError", messages.get("minSizeError"))
                .put("emptyError", messages.get("emptyError"))
                .put("onLeave", messages.get("onLeave"))
                .put("uploadLabel", messages.get("upload-label"))
                .put("dropAreaLabel", messages.get("dropArea-label"))
                .put("cancelLabel", messages.get("cancel-label"))
                .put("failedLabel", messages.get("failed-label"));

        final long sizeLimit = calculateSizeLimit();

        final JSONObject parameter = new JSONObject()
                .put("elementId", getClientId())
                .put("action", resources.createEventLink("upload", context).toURI())
                .put("showMessagesDialog", uploadErrorMesages.getClientId())
                .put("messages", uploadMessages)
                .put("multiple", multiple)
                .put("sizeLimit", sizeLimit)
                .put("maxConnections", maxConnections);

        if (allowedExtensions != null) {

            parameter.put("allowedExtensions", new JSONArray(allowedExtensions));
        }

        JQueryUtils.merge(parameter, params);

        javaScriptSupport.addInitializerCall(getInitMethod(), parameter);

    }

    /**
     * @return 0 if {@link #sizeLimit} is a non valid value or the correct size
     *         limit in bytes.
     */
    private long calculateSizeLimit() {

        if (sizeLimit.matches("^[\\d]+$")) {

            return Long.valueOf(this.sizeLimit);
        }

        double size = Double.valueOf(sizeLimit.substring(0, sizeLimit.length() - 1));
        int i;

        for (i = 0; i < UNITS.length; i++) {

            size = size * 1024;

            if (sizeLimit.endsWith(UNITS[i])) {

                return (long) size;
            }
        }

        return 0;
    }

    private UploadedFile getUploadedFile() {

        if (ajaxDecoder.isAjaxUploadRequest(request)) {

            return ajaxDecoder.getFileUpload();
        }

        return multipartDecoder.getFileUpload(JQueryComponentConstants.FILE_UPLOAD_PARAMETER);
    }

    @OnEvent(value = "upload")
    Object onUpload(EventContext ctx) {
    	System.out.println("UPLOAd");
    	System.out.println(ctx);
    	System.out.println(ctx.getCount());
    	System.out.println(ctx.get(String.class, 0));
        // The parameter 'qqfile' is specified in jquery.fileuploader.js
        UploadedFile uploaded = getUploadedFile();

        if (uploaded != null && StringUtils.isEmpty(uploaded.getFileName())) {
            uploaded = null;
        }

        final Holder<Object> holder = Holder.create();
        final ComponentEventCallback<Object> callback = new ComponentEventCallback<Object>() {

            public boolean handleResult(final Object result) {

                holder.put(result);

                return true;
            }
        };

        final boolean success = uploaded != null;

        if ( ! ajaxDecoder.isAjaxUploadRequest(request)) {
        	
            this.resources.triggerEvent(JQueryEventConstants.NON_XHR_UPLOAD, ArrayUtils.addAll(new Object[]{ uploaded}, ctx.toStrings()), callback);

            return processNonXHRResult(success, holder.get());
        }
        System.out.println(ctx);
        this.resources.triggerEvent(JQueryEventConstants.AJAX_UPLOAD, ArrayUtils.addAll(new Object[]{ uploaded}, ctx.toStrings()), callback);
        return processXHRResult(success, holder.get());
    }

    private Object processXHRResult(final boolean success, final Object triggerResult) {

        final JSONObject result = new JSONObject().put("success", success);
        if (triggerResult != null && triggerResult instanceof JSONObject) {

            JQueryUtils.merge(result, (JSONObject) triggerResult);
            return result;
        }

        ajaxResponseRenderer.addFilter(new PartialMarkupRendererFilter() {

            public void renderMarkup(MarkupWriter writer, JSONObject reply, PartialMarkupRenderer renderer) {

                renderer.renderMarkup(writer, reply);
                JQueryUtils.merge(reply, result);
            }
        });

        return triggerResult;
    }

    private Object processNonXHRResult(boolean success, final Object triggerResult) {

        final JSONObject result = new JSONObject().put("success", success);
        if (triggerResult != null && triggerResult instanceof JSONObject) {

            JQueryUtils.merge(result, (JSONObject) triggerResult);
        }

        return new TextStreamResponse("text/html", result.toCompactString());
    }

}
