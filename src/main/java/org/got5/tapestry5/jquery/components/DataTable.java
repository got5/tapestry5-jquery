//
//Copyright 2010 GOT5 (GO Tapestry 5)
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//

package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.internal.DataTableModel;
import org.got5.tapestry5.jquery.internal.DefaultDataModel;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * @since 2.1.1
 */
@Events(JQueryEventConstants.DATA)
@Import(library = {
		"${assets.path}/components/datatables/jquery.dataTables.min.js",
		"${assets.path}/components/datatables/dataTables.js" }, 
		stylesheet = { "${assets.path}/components/datatables/tango/skin.css" })
public class DataTable extends GridComponent {
	

	@Component(parameters = { "index=inherit:columnIndex", "lean=inherit:lean",
			"overrides=overrides", "model=dataModel" })
	private GridColumns columns;
	
	@Inject
	private ComponentResources resources;

	@Inject
	private TypeCoercer typeCoercer;

	@Environmental
	private JavaScriptSupport support;

	@Inject
	private Request request;

	
	/**
	 * The default Implementation of the DataTableModel Interface
	 */
	private DataTableModel reponse = new DefaultDataModel(typeCoercer);

	/**
	 * Parameter used to write the method called when we use the dataTable via Ajax. the SendResponse method will return the datas
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private DataTableModel dataTableModel;

	/**
	 * JSON options for the DataTable component
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private JSONObject options;

	/**
	 * if false, all the datas will be loaded once. if true, a ajax request will be 
	 * sent each time is needed. 
	 * 
	 * @see DataTableModel
	 */
	@Parameter(value = "false", defaultPrefix = BindingConstants.LITERAL)
	private Boolean mode;

	
	
	/**
	 * Event method in order to get the datas to display.
	 */
	@OnEvent(value = JQueryEventConstants.DATA)
	JSONObject onData() {
		return getDataTModel().sendResponse(request, getSource(), getDataModel(),
				getSortModel(), getOverrides());
	}

	/**
	 * Get the DataTableModel. If the dataTableModel parameter is not bound, we will use
	 * the default implementation
	 */
	private DataTableModel getDataTModel() {
		if (resources.isBound("dataTableModel"))
			return dataTableModel;
		return reponse;
	}

	/**
	 * This method will construct the JSON options and call the DataTable contructor
	 */
	@AfterRender
	void setJS() {

		JSONObject setup = new JSONObject();
		
		setup.put("id", getClientId());

		JSONObject dataTableParams = new JSONObject();

		if (mode) {
			dataTableParams.put("sAjaxSource", resources.createEventLink("data").toAbsoluteURI());
			dataTableParams.put("bServerSide", "true");
			dataTableParams.put("bProcessing", "true");
		}

		dataTableParams.put("sPaginationType", "full_numbers");
		
		dataTableParams.put("iDisplayLength", getRowsPerPage());
		
		dataTableParams.put("aLengthMenu", new JSONLiteral("[[" + getRowsPerPage()
				+ "," + (getRowsPerPage() * 2) + "," + (getRowsPerPage() * 4) + "],["
				+ getRowsPerPage() + "," + (getRowsPerPage() * 2) + ","
				+ (getRowsPerPage() * 4) + "]]"));
		
		JQueryUtils.merge(dataTableParams, options);
		
		//We set the bSortable parameters for each column. Cf : http://www.datatables.net/usage/columns
		JSONArray sortableConfs = new JSONArray();
		for(String propertyName : getPropertyNames()){
			sortableConfs.put(new JSONObject(String.format("{'bSortable': %s}", getModel().get(propertyName).isSortable())));
		}
		
		dataTableParams.put("aoColumns", sortableConfs);
		
		setup.put("params", dataTableParams);

		support.addInitializerCall("dataTable", setup);
	}

	public Boolean getMode() { return mode;	}
}
