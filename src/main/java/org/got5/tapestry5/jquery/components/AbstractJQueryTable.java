package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.internal.DataTableModel;
import org.got5.tapestry5.jquery.internal.TableInformation;

/**
 * @tapestrydoc
 */
public abstract class AbstractJQueryTable extends AbstractTable {

	/**
	 * if false, all the datas will be loaded once. if true, a ajax request will
	 * be sent each time is needed.
	 * 
	 * @see DataTableModel
	 */
	@Parameter(value = "false", defaultPrefix = BindingConstants.LITERAL)
	private Boolean mode;

	/**
	 * JSON options for the DataTable component
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private JSONObject options;

	/**
	 * Parameter used to write the method called when we use the dataTable via
	 * Ajax. the SendResponse method will return the datas
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private DataTableModel dataTableModel;

	@Component(parameters = { "index=inherit:columnIndex", "lean=inherit:lean",
			"overrides=overrides", "model=dataModel", "mode=true" })
	private GridColumns headers;

	@Component(parameters = { "index=inherit:columnIndex", "lean=inherit:lean",
			"overrides=overrides", "model=dataModel", "mode=false" })
	private GridColumns footers;

	@Inject
	private ComponentResources resources;

	public Boolean getMode() {
		return mode;
	}

	public JSONObject getOptions() {
		return options;
	}

	public DataTableModel getDataTableModel() {
		return dataTableModel;
	}

	/**
	 * Get the DataTableModel. If the dataTableModel parameter is not bound, we
	 * will use the default implementation
	 */
	public DataTableModel getDataTModel() {
		if (resources.isBound("dataTableModel")) {
			return getDataTableModel();
		}
		return getDefaultDataTableModel();
	}

	protected abstract DataTableModel getDefaultDataTableModel();

	
}
