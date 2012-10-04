package org.got5.tapestry5.jquery.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;

public class AjaxUploadTest {

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String message;

    @Persist
    @Property
    private List<UploadedFile> uploadedFiles;

    @InjectComponent
    private Zone uploadResult;

    @Inject
    private AjaxResponseRenderer renderer;
    
    void onActivate() {

        if (uploadedFiles == null)
            uploadedFiles = new ArrayList<UploadedFile>();
    }

    @OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {

        this.uploadedFiles.add(uploadedFile);
        
        renderer.addRender("uploadResult", uploadResult.getBody());
    }

    void onUploadException(FileUploadException ex) {

        message = "Upload exception: " + ex.getMessage();

        renderer.addRender("uploadResult", uploadResult.getBody());
    }
}
