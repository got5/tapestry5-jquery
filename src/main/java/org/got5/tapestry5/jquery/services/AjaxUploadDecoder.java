package org.got5.tapestry5.jquery.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;

public interface AjaxUploadDecoder {

    boolean isAjaxUploadRequest(HttpServletRequest request);

    boolean isAjaxUploadRequest(Request request);

    UploadedFile getFileUpload();

    void setupUploadedFile(HttpServletRequest request);

}
