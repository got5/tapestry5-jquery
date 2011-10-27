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

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
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
	private DataTableColumns columns;
	
	@Inject
	private ComponentResources resources;

	@Inject
	private TypeCoercer typeCoercer;

	@Environmental
	private JavaScriptSupport support;

	@Inject
	private Request request;

	
	/**
	 * TODO JavaDoc
	 */
	private DataTableModel reponse = new DefaultDataModel(typeCoercer);

	/**
	 * TODO JavaDoc
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private DataTableModel dataTableModel;

	/**
	 * TODO JavaDoc
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private JSONObject options;

	/**
	 * TODO JavaDoc
	 */
	@Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
	private Boolean mode;

	/**
	 * TODO JavaDoc
	 */
	@Property
	private Integer index;

	/**
	 * TODO JavaDoc
	 */
	@Property
	private String cellModel;
	
	/**
	 * TODO JavaDoc
	 */
	@OnEvent(value = JQueryEventConstants.DATA)
	JSONObject onData() {
		return getDataTModel().sendResponse(request, getSource(), getDataModel(),
				getSortModel(), getOverrides());
	}

	/**
	 * TODO JavaDoc
	 */
	private DataTableModel getDataTModel() {
		if (resources.isBound("dataTableModel"))
			return dataTableModel;
		return reponse;
	}

	/**
	 * TODO Javadoc
	 */
	public String getCellValue() {

		Object obj = getSource().getRowValue(index);

		PropertyConduit conduit = getDataModel().get(cellModel).getConduit();

		Class type = conduit.getPropertyType();

		String cellValue;

		Object val = conduit.get(obj);

		if (val == null)
			cellValue = "";
		else {

			try {

				if (type.equals(Date.class)) {
					
					DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, request.getLocale());

					cellValue = dateFormat.format((Date) val);
					
				} else if (type.equals(Enum.class)) {
					cellValue = TapestryInternalUtils.getLabelForEnum(getOverrides().getOverrideMessages(), (Enum) val);
				} else {
					cellValue = typeCoercer.coerce(val, String.class);
				}

			} catch (NullPointerException ex) {

				cellValue = "undefined " + cellModel;

			}
		}
		return cellValue;
	}

	/**
	 * TODO Javadoc
	 */
	public List<String> getPropertyNames() {
		return (List<String>) getDataModel().getPropertyNames();
	}

	/**
	 * TODO Javadoc
	 */
	public Iterable<Integer> getLoopSource() {
		return new Iterable<Integer>() {

			public Iterator<Integer> iterator() {

				return new Iterator<Integer>() {

					Integer i = new Integer(0);

					public boolean hasNext() {
						return i < getSource().getAvailableRows();
					}

					public Integer next() {
						return i++;
					}

					public void remove() {
						i = 0;
					}
				};

			}
		};
	}

	
	/**
	 * TODO JavaDoc
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

		setup.put("params", dataTableParams);

		support.addInitializerCall("dataTable", setup);
	}

	public Boolean getMode() { return mode;	}
}
