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

package org.got5.tapestry5.jquery.test.pages.test;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.test.data.Celebrity;
import org.got5.tapestry5.jquery.test.data.CelebritySource;
import org.got5.tapestry5.jquery.test.data.IDataSource;

@Import(stylesheet ={ "context:dataTables/css/demo_table_jui.css",
					  "context:dataTables/css/demo_page.css",	
					  "context:dataTables/css/demo_table.css",
					  "context:dataTables/ColVis/media/css/ColVis.css", 
					  "context:dataTables/ColReorder/media/css/ColReorder.css", 
					  "context:dataTables/TableTools/css/TableTools.css"})
public class DataTables
{
	@SessionState
	private IDataSource dataSource;
	private Celebrity celebrity;
	private CelebritySource celebritySource;
	
	@Property
	private Celebrity current;
	
	public GridDataSource getCelebritySource() {
		if(celebritySource==null)
			celebritySource = new CelebritySource(dataSource);
		return celebritySource;
	}

	public List<Celebrity> getAllCelebrities() {
		System.out.println("Getting all celebrities...");
		return dataSource.getAllCelebrities();
	}

	public Celebrity getCelebrity() {
		return celebrity;
	}
	
	public void setCelebrity(Celebrity celebrity) {
		this.celebrity = celebrity;
	}
	
	@Inject
	private ComponentResources resources;

	
	@Inject
	private BeanModelSource beanModelSource;
	
	@SuppressWarnings("unchecked")
	private BeanModel model;
	
	@SuppressWarnings("unchecked")
	public BeanModel getModel() {
		this.model = beanModelSource.createDisplayModel(Celebrity.class,resources.getMessages());
		this.model.get("firstName").sortable(false);
		return model;
	}
	
	public JSONObject getOptions(){
		
		JSONObject json = new JSONObject("bJQueryUI", "true", "bStateSave", "true", "sDom", "TC<\"clear\">Rlfrtip");
		
		JSONObject dataTable = new JSONObject();
		dataTable.put("sSwfPath", as.getContextAsset("dataTables/TableTools/swf/copy_cvs_xls_pdf.swf", null).toClientURL());
		
		json.put("oTableTools", dataTable);
		
		//These parameters are not a DataTable. They are used to get more information about a row
		json.put("ajaxUrl", resources.createEventLink("extraInfo", null).toURI());
		json.put("openImg", as.getContextAsset("img/details_open.png", null).toClientURL());
		json.put("closeImg", as.getContextAsset("img/details_close.png", null).toClientURL());
		
		return json;
	}
	
	@Inject
	private JavaScriptSupport js;
	
	@Inject
	private AssetSource as;
	
	@AfterRender
	public void addJsFile(){
		js.importJavaScriptLibrary(as.getContextAsset("dataTables/ColVis/media/js/ColVis.js", null));
		js.importJavaScriptLibrary(as.getContextAsset("dataTables/ColReorder/media/js/ColReorder.js", null));
		js.importJavaScriptLibrary(as.getContextAsset("dataTables/TableTools/js/ZeroClipboard.js", null));
		js.importJavaScriptLibrary(as.getContextAsset("dataTables/TableTools/js/TableTools.js", null));
		js.importJavaScriptLibrary(as.getContextAsset("js/demo.js", null));
	}
	
	@OnEvent(value="extrainfo")
	public JSONObject sendResponse(@RequestParameter(value = "name") String name){
		return new JSONObject("name", name);
	}
	
	
}
