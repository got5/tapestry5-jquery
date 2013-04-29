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
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.internal.services.AjaxPartialResponseRenderer;
import org.apache.tapestry5.internal.services.PageRenderQueue;
import org.apache.tapestry5.internal.services.ajax.AjaxFormUpdateController;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.TranslatorSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.internal.DataTableModel;
import org.got5.tapestry5.jquery.internal.DefaultDataTableModel;
import org.got5.tapestry5.jquery.internal.FakeInheritedBinding;
import org.got5.tapestry5.jquery.services.javascript.DataTableStack;
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * @since 2.1.1
 *
 *        For more informations about how to translate the DataTable plug-in,
 *        please visit the official documentation website {@link http
 *        ://datatables.net/plug-ins/i18n}
 * @tapestrydoc
 */
@Events(JQueryEventConstants.DATA)
@Import(stack = DataTableStack.STACK_ID)
public class DataTable extends AbstractJQueryTable {

	@Inject
	private ComponentResources resources;

	@Inject
	private TypeCoercer typeCoercer;

	@Inject
	private TranslatorSource ts;

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
	 * Event method in order to get the datas to display.
	 *
	 * @throws IOException
	 */
	@OnEvent(value = JQueryEventConstants.DATA)
	JSONObject onData() throws IOException {
		/**
		 * If ajax mode, we filter on server-side, otherwise, we filter from the
		 * available data already loaded (see DefaultDataTableModel#filterData)
		 */
		if (getMode()) {
			/**
			 * Give a chance to the developer to update the GridDataSource to
			 * filter data server-side
			 * */
			resources
					.triggerEvent(JQueryEventConstants.FILTER_DATA, null, null);
			/**
			 * Give a chance to the developer to sort the GridDataSource
			 * server-side
			 * */
			resources.triggerEvent(JQueryEventConstants.SORT_DATA, null, null);
		}

		return getDataTModel().sendResponse(request, getSource(),
				getDataModel(), getSortModel(), getOverrides(), getMode());
	}

	public DataTableModel getDefaultDataTableModel() {
		return reponse;
	}

	/**
	 * This method will construct the JSON options and call the DataTable
	 * contructor
	 */
	@AfterRender
	void setJS() {

		JSONObject setup = new JSONObject();

		setup.put("id", getClientId());

		JSONObject dataTableParams = new JSONObject();

		if (getMode()) {
			dataTableParams.put("sAjaxSource", resources
					.createEventLink("data").toAbsoluteURI());
			dataTableParams.put("bServerSide", "true");
			dataTableParams.put("bProcessing", "true");
		}

		dataTableParams.put("sPaginationType", "full_numbers");

		dataTableParams.put("iDisplayLength", getRowsPerPage());

		dataTableParams
				.put("aLengthMenu", new JSONLiteral("[[" + getRowsPerPage()
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
			confs.put("mDataProp", propertyName);
			confs.put("bSortable", getModel().get(propertyName).isSortable());
			columnConfs.put(confs);

		}

		dataTableParams.put("aoColumns", columnConfs);

		dataTableParams.put("oLanguage", setI18NMessages());

		JQueryUtils.merge(dataTableParams, getOptions());
		setup.put("params", dataTableParams);

		support.addInitializerCall("dataTable", setup);
	}

	private JSONObject setI18NMessages() {
		JSONObject language = new JSONObject();
		language.put("sProcessing", messages.get("datatable.sProcessing"));
		language.put("sSearch", messages.get("datatable.sSearch"));
		language.put("sLengthMenu", messages.get("datatable.sLengthMenu"));
		language.put("sInfo", messages.get("datatable.sInfo"));
		language.put("sInfoEmpty", messages.get("datatable.sInfoEmpty"));
		language.put("sInfoFiltered", messages.get("datatable.sInfoFiltered"));
		language.put("sInfoPostFix", messages.get("datatable.sInfoPostFix"));
		language.put("sLoadingRecords", messages.get("datatable.sLoadingRecords"));
		language.put("sZeroRecords", messages.get("datatable.sZeroRecords"));
		language.put("sEmptyTable", messages.get("datatable.sEmptyTable"));
		language.put("oPaginate", new JSONObject(
				"sFirst", messages.get("datatable.oPaginate.sFirst"),
				"sPrevious", messages.get("datatable.oPaginate.sPrevious"),
				"sNext", messages.get("datatable.oPaginate.sNext"),
				"sLast", messages.get("datatable.oPaginate.sLast")));
		language.put("oAria", new JSONObject(
				"sSortAscending", messages.get("datatable.oAria.sSortAscending"),
				"sSortDescending", messages.get("datatable.oAria.sSortDescending")));

		return language;
	}

}
