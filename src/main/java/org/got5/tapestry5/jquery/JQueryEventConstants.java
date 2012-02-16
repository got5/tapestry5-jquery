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
     * Triggered by the ZoneRefresh component
     */
    public static final String REFRESH = "Refresh";
    
    
    /**
     * Triggered by the ZoneDrop component
     */
    public static final String DROP = "Drop";
    
    /**
     * Triggered by the {@link Tabs} component whenever a tab is changed.
     */
    public static final String SELECT_TAB = "SelectTab";
    
    /**
     * Triggered by the {@link Sortable} mixin
     */
    public static final String SORTABLE = "sort";
}
