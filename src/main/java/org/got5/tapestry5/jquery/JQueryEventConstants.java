package org.got5.tapestry5.jquery;

import org.apache.tapestry5.EventConstants;
import org.got5.tapestry5.jquery.components.AjaxUpload;

/**
 * Analog to {@link EventConstants}-
 *
 * @author criedel
 */
public class JQueryEventConstants {

    /**
     * Triggered when a file has been uploaded via {@link AjaxUpload}.
     */
    public static final String AJAX_UPLOAD = "ajaxFileUpload";

    /**
     * Triggered when a file has been uploaded via {@link AjaxUpload} via a regular post (applies to IE browsers).
     */
    public static final String NON_XHR_UPLOAD = "fileUpload";

    /**
     * Triggered by the DataTable component
     */
    public static final String DATA = "Data";
    
    
    /**
     * Triggered by the DataTable component to give a chance to the developer to filter data on server-side
     */
    public static final String FILTER_DATA = "filterData";
    
    
    /**
     * Triggered by the DataTable component to give a chance to the developer to sort data on server-side
     */
    public static final String SORT_DATA = "sortData";
    
    
    /**
     * Triggered by the ZoneRefresh component
     */
    public static final String REFRESH = "Refresh";
    
    
    /**
     * Triggered by the ZoneDrop component
     */
    public static final String DROP = "Drop";
    
}
