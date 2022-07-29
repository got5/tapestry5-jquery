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

import java.io.IOException;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.internal.services.AjaxPartialResponseRenderer;
import org.apache.tapestry5.internal.services.PageRenderQueue;
import org.apache.tapestry5.internal.services.ajax.AjaxFormUpdateController;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.commons.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.services.TranslatorSource;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.internal.DataTableModel;
import org.got5.tapestry5.jquery.internal.DefaultDataTableModel;
import org.got5.tapestry5.jquery.internal.FakeInheritedBinding;
import org.got5.tapestry5.jquery.utils.JQueryUtils;


/**
 * @since 2.1.1
 *
 *        For more information about how to translate the DataTable plug-in,
 *        please visit the official documentation website
 *
 * @tapestrydoc
 */
@Events(JQueryEventConstants.DATA)
public class DataTable extends AbstractJQueryTable {

	@Inject
	private ComponentResources resources;

	@Inject
	private TypeCoercer typeCoercer;

	@Inject
    protected TranslatorSource ts;

	@Environmental
	private JavaScriptSupport support;

	@Inject
	private Request request;

	@Inject
	private Messages messages;

	@Inject
	private PageRenderQueue pageRenderQueue;

	@Inject
	private AjaxFormUpdateController ajaxFormUpdateController;

	@Inject
	private AjaxPartialResponseRenderer partialRenderer;

	/**
	 * The default Implementation of the DataTableModel Interface
	 */
	private DataTableModel reponse = new DefaultDataTableModel(typeCoercer, ts, pageRenderQueue,
			ajaxFormUpdateController, partialRenderer,
			new FakeInheritedBinding() {
				public void set(Object value) {
					setRow(value);
				}
			},
			new FakeInheritedBinding() {
				public void set(Object value) {
					setRowIndex((Integer) value);
				}
			}
	);


	/**
	 * Event method in order to get the data to display.
	 *
	 * @throws IOException in case there was an error rendering during rendering
	 */
	@OnEvent(value = JQueryEventConstants.DATA)
	JSONObject onData() throws IOException {
		/*
		 * If ajax mode, we filter on server-side, otherwise, we filter from the
		 * available data already loaded (see DefaultDataTableModel#filterData)
		 */
		if (getMode()) {
			/*
			 * Give a chance to the developer to update the GridDataSource to
			 * filter data server-side
			 */
			resources.triggerEvent(JQueryEventConstants.FILTER_DATA, null, null);
			/*
			 * Give a chance to the developer to sort the GridDataSource
			 * server-side
			 */
			resources.triggerEvent(JQueryEventConstants.SORT_DATA, null, null);
		}

		return getDataTModel().sendResponse(request, getSource(),
				getDataModel(), getSortModel(), getOverrides(), getMode());
	}

	@Override
    public DataTableModel getDefaultDataTableModel() {
		return reponse;
	}

	/**
	 * This method will construct the JSON options and call the DataTable
	 * constructor
	 */
	@AfterRender
	void setJS() {

		JSONObject setup = new JSONObject();

		setup.put("id", getClientId());

		JSONObject dataTableParams = new JSONObject();

		if (getMode()) {
			dataTableParams.put("ajax", resources
					.createEventLink("data").toAbsoluteURI());
			dataTableParams.put("serverSide", "true");
			dataTableParams.put("processing", "true");
		}

		dataTableParams.put("pagingType", "full_numbers");

		dataTableParams.put("pageLength", getRowsPerPage());

		dataTableParams
				.put("lengthMenu", new JSONLiteral("[[" + getRowsPerPage()
						+ "," + (getRowsPerPage() * 2) + ","
						+ (getRowsPerPage() * 4) + "],[" + getRowsPerPage()
						+ "," + (getRowsPerPage() * 2) + ","
						+ (getRowsPerPage() * 4) + "]]"));

		// We set the bSortable parameters for each column. Cf :
		// http://www.datatables.net/usage/columns
		// We set also the mDataProp parameters to handle ColReorder plugin. Cf
		// :
		// http://datatables.net/release-datatables/extras/ColReorder/server_side.html
		JSONArray columnConfs = new JSONArray();
		for (String propertyName : getPropertyNames()) {
			JSONObject confs = new JSONObject();
			confs.put("data", propertyName);
			confs.put("orderable", getModel().get(propertyName).isSortable());
			columnConfs.put(confs);

		}

		dataTableParams.put("columns", columnConfs);

		dataTableParams.put("language", setI18NMessages());

        JQueryUtils.merge(dataTableParams, getOptions());
		setup.put("params", dataTableParams);

		support.require("tjq/dataTables").priority(InitializationPriority.EARLY).with(setup);
	}

	private JSONObject setI18NMessages() {
		JSONObject language = new JSONObject();
		language.put("processing", messages.get("datatable.sProcessing"));
		language.put("search", messages.get("datatable.sSearch"));
		language.put("lengthMenu", messages.get("datatable.sLengthMenu"));
		language.put("info", messages.get("datatable.sInfo"));
		language.put("infoEmpty", messages.get("datatable.sInfoEmpty"));
		language.put("infoFiltered", messages.get("datatable.sInfoFiltered"));
		language.put("infoPostFix", messages.get("datatable.sInfoPostFix"));
		language.put("loadingRecords", messages.get("datatable.sLoadingRecords"));
		language.put("zeroRecords", messages.get("datatable.sZeroRecords"));
		language.put("emptyTable", messages.get("datatable.sEmptyTable"));
		language.put("paginate", new JSONObject(
				"first", messages.get("datatable.oPaginate.sFirst"),
				"previous", messages.get("datatable.oPaginate.sPrevious"),
				"next", messages.get("datatable.oPaginate.sNext"),
				"last", messages.get("datatable.oPaginate.sLast")));
		language.put("aria", new JSONObject(
				"sortAscending", messages.get("datatable.oAria.sSortAscending"),
				"sortDescending", messages.get("datatable.oAria.sSortDescending")));

		return language;
	}

}
