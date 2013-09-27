//
// Copyright 2010 GOT5 (GO Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.DataTableConstants;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.data.Celebrity;

public class DataTablesAjax extends BaseDataTables
{
    private static final String EVENT = "extraInfo";
	/**
	 * For ajax mode, annotate your current item with @Environmental
	 * The same for any iteration object used in propertyOverride block for ajax mode (index, ...)
	 * */
	@Environmental
	@Property
	private Celebrity current;
		
	@Environmental
	@Property
	private int index;

    @Inject
    private JavaScriptSupport js;

    @Inject
    private AssetSource as;

    @Inject
    private Request request;
	
	public JSONObject getOptions(){
		
		JSONObject json = new JSONObject("bJQueryUI", "true", "bStateSave", "true", "sDom", "TC<\"clear\">Rlfrtip");
		
		JSONObject dataTable = new JSONObject();
		dataTable.put("sSwfPath", as.getContextAsset("dataTables/TableTools/media/swf/copy_csv_xls_pdf.swf", null).toClientURL());
		
		json.put("oTableTools", dataTable);
		
		//These parameters are not a DataTable. They are used to get more information about a row
		json.put("ajaxUrl", resources.createEventLink(EVENT, null).toURI());
		json.put("openImg", as.getContextAsset("img/details_open.png", null).toClientURL());
		json.put("closeImg", as.getContextAsset("img/details_close.png", null).toClientURL());
		
		return json;
	}
	
    @AfterRender
	public void addJsFile(){
        js.importJavaScriptLibrary(as.getContextAsset("js/demo.js", null));
	}
	
	@OnEvent(value = EVENT)
	public JSONObject sendResponse(@RequestParameter(value = "name") String name){
		return new JSONObject("name", name);
	}
	
    /**
	 * Event handler method called when datatable's search field is used
	 * Gives a chance to the user to filter data in lazy-loading mode (mode=true)
	 * */
	@OnEvent(value= JQueryEventConstants.FILTER_DATA, component="datatableAjax")
	public void filterData(){
		String val = request.getParameter(DataTableConstants.SEARCH); 
		getDataSource().filter(val);
	}
	
}
