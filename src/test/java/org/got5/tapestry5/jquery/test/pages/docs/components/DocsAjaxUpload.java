package org.got5.tapestry5.jquery.test.pages.docs.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.components.AjaxUpload;
import org.got5.tapestry5.jquery.utils.JQueryTabData;

/**
 * Ajax and non-ajax upload demo page.
 *
 * @author criedel
 */
public class DocsAjaxUpload {

    @Persist(PersistenceConstants.FLASH)
    private String message;

    @Persist
    @Property
    private List<UploadedFile> uploadedFiles;

    @InjectComponent
    private Zone uploadResult;

    @Inject
    private ComponentResources resources;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    void onActivate() {

        if (uploadedFiles == null)
            uploadedFiles = new ArrayList<UploadedFile>();
    }

    public List<JQueryTabData> getListTabData() {

        List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();

        listTabData.add(new JQueryTabData("Documentation", "docs"));
        listTabData.add(new JQueryTabData("JSONParameters", "JSONParameters"));
        listTabData.add(new JQueryTabData("Example", "example"));

        return listTabData;

    }

    @OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {

        if (uploadedFile != null) {

            this.uploadedFiles.add(uploadedFile);
        }

        message = "This upload was: AJAX_UPLOAD";

        ajaxResponseRenderer.addRender("uploadResult", uploadResult);
    }

    @OnEvent(component = "uploadImage", value = JQueryEventConstants.NON_XHR_UPLOAD)
    Object onNonXHRImageUpload(UploadedFile uploadedFile) {

        if (uploadedFile != null) {

            this.uploadedFiles.add(uploadedFile);
        }

        final JSONObject result = new JSONObject();
        final JSONObject params = new JSONObject()
              .put("url", resources.createEventLink("myCustomEvent", "NON_XHR_UPLOAD").toURI())
              .put("zoneId", "uploadResult");

        result.put(AjaxUpload.UPDATE_ZONE_CALLBACK, params);

        return result;
    }

    @OnEvent(value = "myCustomEvent")
    void onMyCustomEvent(final String someParam) {

        message = "This upload was: " + someParam;

        ajaxResponseRenderer.addRender("uploadResult", uploadResult);
    }

    void onUploadException(FileUploadException ex) {

        message = "Upload exception: " + ex.getMessage();

        ajaxResponseRenderer.addRender("uploadResult", uploadResult);
    }

    public String getMessage() {

        return message;
    }

}
