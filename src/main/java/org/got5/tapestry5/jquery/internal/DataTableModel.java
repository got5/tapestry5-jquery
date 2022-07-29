package org.got5.tapestry5.jquery.internal;

import java.io.IOException;

import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.http.services.Request;

/**
 * Interface used when your dataTable load the data by using ajax. You will have
 * to implement the sendResponse.
 */
public interface DataTableModel {

    JSONObject sendResponse(Request request, GridDataSource source, BeanModel model, GridSortModel sortModel, PropertyOverrides overrides, boolean mode)
            throws IOException;
}
