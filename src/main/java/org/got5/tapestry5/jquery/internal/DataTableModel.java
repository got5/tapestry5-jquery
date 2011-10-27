package org.got5.tapestry5.jquery.internal;

import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

/**
 * TODO
 */
public interface DataTableModel {
	
	
	public abstract JSONObject sendResponse(Request request,
			GridDataSource source, BeanModel model, GridSortModel sortModel, PropertyOverrides overrides);
	
}
