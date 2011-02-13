package org.got5.tapestry5.jquery.components;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.services.javascript.AjaxUploadStack;

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
 */
@Events( { JQueryEventConstants.AJAX_UPLOAD } )
@Import(stack = AjaxUploadStack.STACK_ID)
public class AjaxUpload implements ClientElement {

    /**
     * The uploaded file.
     */
    @Parameter(required = true, principal = true, autoconnect = true)
    private UploadedFile value;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private MultipartDecoder decoder;

    @Inject
    private Request request;

    @InjectComponent
    private Dialog uploadErrorMesages;

    private String clientId;

    @BeginRender
    void render(MarkupWriter writer) {

        JSONObject parameter = new JSONObject("elementId", getClientId(),
                "action", resources.createEventLink("upload").toURI(),
                "showMessagesDialog", uploadErrorMesages.getClientId());

        javaScriptSupport.addInitializerCall("uploadable", parameter);
    }

    @AfterRender
    void afterRender(MarkupWriter writer) {

    }

    public String getClientId() {

        if (clientId == null) {

            clientId = javaScriptSupport.allocateClientId(resources);
        }

        return clientId;
    }

    @OnEvent(value = "upload")
    Object onUpload() {

        // The parameter 'qqfile' is specified in jquery.fileuploader.js
        UploadedFile uploaded = decoder.getFileUpload("qqfile");

        if (uploaded != null && StringUtils.isEmpty(uploaded.getFileName())) {
            uploaded = null;
        }

        this.value = uploaded;
        this.resources.triggerEvent(JQueryEventConstants.AJAX_UPLOAD, new Object[]{}, null);

        final JSONObject result = new JSONObject("success", Boolean.toString(uploaded != null));
        if (request.isXHR()) {
            return result;
        }

        return new TextStreamResponse("text/html", result.toCompactString());

    }
}
