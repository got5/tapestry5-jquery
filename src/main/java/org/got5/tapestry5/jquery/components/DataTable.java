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

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
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
import org.got5.tapestry5.jquery.utils.JQueryUtils;

/**
 * @since 2.1.1
 * 
 * @tapestrydoc
 */
@Events(JQueryEventConstants.DATA)
@Import(library = {
		"${assets.path}/components/datatables/jquery.dataTables.min.js",
		"${assets.path}/components/datatables/dataTables.js" }, 
		stylesheet = { "${assets.path}/components/datatables/tango/skin.css" })
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
	/**
	 * The default Implementation of the DataTableModel Interface
	 */
	private DataTableModel reponse = new DefaultDataTableModel(typeCoercer, ts);

	/**
	 * Event method in order to get the datas to display.
	 */
	@OnEvent(value = JQueryEventConstants.DATA)
	JSONObject onData() {
		return getDataTModel().sendResponse(request, getSource(), getDataModel(),
				getSortModel(), getOverrides());
	}

	
	public DataTableModel getDefaultDataTableModel(){return reponse;}
	
	

	/**
	 * This method will construct the JSON options and call the DataTable contructor
	 */
	@AfterRender
	void setJS() {

		JSONObject setup = new JSONObject();
		
		setup.put("id", getClientId());

		JSONObject dataTableParams = new JSONObject();

		if (getMode()) {
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
		
		JQueryUtils.merge(dataTableParams, getOptions());
		
		//We set the bSortable parameters for each column. Cf : http://www.datatables.net/usage/columns
		JSONArray sortableConfs = new JSONArray();
		for(String propertyName : getPropertyNames()){
			sortableConfs.put(new JSONObject(String.format("{'bSortable': %s}", getModel().get(propertyName).isSortable())));
		}
		
		dataTableParams.put("aoColumns", sortableConfs);
		
		
		
		JSONObject language = new JSONObject();
        language.put("sProcessing", messages.get("datatable.sProcessing"));
        language.put("sLengthMenu", messages.get("datatable.sLengthMenu"));
        language.put("sZeroRecords", messages.get("datatable.sZeroRecords"));
        language.put("sEmptyTable", messages.get("datatable.sEmptyTable"));
        language.put("sLoadingRecords", messages.get("datatable.sLoadingRecords"));
        language.put("sInfo", messages.get("datatable.sInfo"));
        language.put("sInfoEmpty", messages.get("datatable.sInfoEmpty"));
        language.put("sInfoFiltered", messages.get("datatable.sInfoFiltered"));
        language.put("sInfoPostFix", messages.get("datatable.sInfoPostFix"));
        language.put("sSearch", messages.get("datatable.sSearch"));
        language.put("sUrl", messages.get("datatable.sUrl"));
        dataTableParams.put("oLanguage", language);
        
		setup.put("params", dataTableParams);

		support.addInitializerCall("dataTable", setup);
	}

}
