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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.data.GridPagerPosition;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.beaneditor.BeanModelUtils;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ClientBehaviorSupport;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.Core;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.internal.DataTableModel;
import org.got5.tapestry5.jquery.internal.DefaultDataModel;
import org.got5.tapestry5.jquery.utils.JQueryUtils;


/**
 * @since 2.1.1
 */
@Import(library = {"${assets.path}/components/datatables/jquery.dataTables.min.js",
				  "${assets.path}/components/datatables/dataTables.js"},
		stylesheet={"${assets.path}/components/datatables/tango/skin.css"})
@Events(JQueryEventConstants.DATA)
public class DataTable implements ClientElement
{
    /**
     * The source of data for the Grid to display. This will usually be a List or array but can also be an explicit
     * {@link GridDataSource}. For Lists and object arrays, a GridDataSource is created automatically as a wrapper
     * around the underlying List.
     */
    @Parameter(required = true, autoconnect = true)
    private GridDataSource source;

   
    /**
     * The number of rows of data displayed on each page. If there are more rows than will fit, the Grid will divide up
     * the rows into "pages" and (normally) provide a pager to allow the user to navigate within the overall result
     * set.
     */
    @Parameter("25")
    private int rowsPerPage;

    /**
     * Defines where the pager (used to navigate within the "pages" of results) should be displayed: "top", "bottom",
     * "both" or "none".
     */
    @Parameter(value = "top", defaultPrefix = BindingConstants.LITERAL)
    private GridPagerPosition pagerPosition;

  

    /**
     * The model used to identify the properties to be presented and the order of presentation. The model may be
     * omitted, in which case a default model is generated from the first object in the data source (this implies that
     * the objects provided by the source are uniform). The model may be explicitly specified to override the default
     * behavior, say to reorder or rename columns or add additional columns. The add, include,
     * exclude and reorder
     * parameters are <em>only</em> applied to a default model, not an explicitly provided one.
     */
    @Parameter
    private BeanModel model;

    /**
     * The model parameter after modification due to the add, include, exclude and reorder parameters.
     */
    private BeanModel dataModel;

    /**
     * The model used to handle sorting of the Grid. This is generally not specified, and the built-in model supports
     * only single column sorting. The sort constraints (the column that is sorted, and ascending vs. descending) is
     * stored as persistent fields of the Grid component.
     */
    @Parameter
    private GridSortModel sortModel;

    /**
     * A comma-seperated list of property names to be added to the {@link org.apache.tapestry5.beaneditor.BeanModel}.
     * Cells for added columns will be blank unless a cell override is provided. This parameter is only used
     * when a default model is created automatically.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String add;

    /**
     * A comma-separated list of property names to be retained from the
     * {@link org.apache.tapestry5.beaneditor.BeanModel}.
     * Only these properties will be retained, and the properties will also be reordered. The names are
     * case-insensitive. This parameter is only used
     * when a default model is created automatically.
     */
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String include;

    /**
     * A comma-separated list of property names to be removed from the {@link org.apache.tapestry5.beaneditor.BeanModel}
     * .
     * The names are case-insensitive. This parameter is only used
     * when a default model is created automatically.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String exclude;

    /**
     * A comma-separated list of property names indicating the order in which the properties should be presented. The
     * names are case insensitive. Any properties not indicated in the list will be appended to the end of the display
     * order. This parameter is only used
     * when a default model is created automatically.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String reorder;


    @Inject
    private ValueEncoderSource encoderSource;

   
    /**
     * The current value, set before the component renders its body.
     */
    @SuppressWarnings("unused")
    @Parameter
    private Object value;
    
    /**
     * If true, then the Grid will be wrapped in an element that acts like a
     * {@link org.apache.tapestry5.corelib.components.Zone}; all the paging and sorting links will refresh the zone,
     * repainting
     * the entire grid within it, but leaving the rest of the page (outside the zone) unchanged.
     */
    @Parameter
    private boolean inPlace;


    @Persist
    private Integer currentPage;

    @Persist
    private String sortColumnId;

    @Persist
    private Boolean sortAscending;

    @Inject
    private ComponentResources resources;

    @Inject
    private BeanModelSource modelSource;
    
    @Inject
    @Core
    private BeanBlockSource defaultBeanBlockSource;
    
    @Inject
    private TypeCoercer typeCoercer;

    @Environmental
    private ClientBehaviorSupport clientBehaviorSupport;
 

    
	 /**
	* Request attribute set to true if localization for the client-side jqgrid has been
	* configured. Used to ensure
	* that this only occurs once, regardless of how many DateFields are on the page.
	*/
	 static final String LOCALIZATION_CONFIGURED_FLAG = "jquery.jqgrid.localization-configured";
	
	
	 @Environmental
	 private JavaScriptSupport support;
	
	
	 @Inject
	 private Request request;
	
	 @Inject
	 private Locale locale;
	
	 @Inject
	 private ComponentDefaultProvider defaultProvider;
	
	 @Inject
	 private JavaScriptSupport javaScriptSupport;
	 
	
	 @Inject
	 private Messages messages;
	 
	 private String clientId;

	 /**
	 * TODO
	 */
	private DataTableModel reponse = new DefaultDataModel(typeCoercer);
	 
	 /**
	 * TODO
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private DataTableModel dataTableModel;
	
	/**
	 * TODO
	 */
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private JSONObject options;
	
	/**
	 * TODO
	 */
	@Parameter(value="true", defaultPrefix = BindingConstants.LITERAL)
	private Boolean mode;

	
/**
* Ajax event handler, form client side to get the data to display
* to parse it according to the server-side format. 
* see http://www.datatables.net/ for more details
*/
 @OnEvent(value=JQueryEventConstants.DATA)
 JSONObject onData()
 { 
	 return getDataTModel().sendResponse(request, source, getDataModel(), getSortModel());
 }
 
 /**
 * TODO
 */
private DataTableModel getDataTModel(){
	 if(resources.isBound("dataTableModel")) return dataTableModel;
	 return reponse;
 }

 @BeginRender
 void beginRender(MarkupWriter writer)
 {
	
	 String clientId = getClientId();

     writer.element("table","id", clientId, "width", "100%", "class", "display");
     writer.element("thead");
     writer.element("tr");
 
     List<String> names = getDataModel().getPropertyNames();
	 for (String name: names)
	 {
		 writer.element("th");
		 writer.write(getDataModel().get(name).getLabel());
		 writer.end();
	 }  

	 writer.end();//</tr>
	 writer.end();//</thead>

	 writer.element("tbody");
	 
	
	 
	 if(mode){
		 writer.element("tr");
		 writer.element("td");
		 writer.write("Loading data from server");
		 writer.end();//</td>
		 writer.end();
	 }
	 else{
	 	 
		 for(int index=0;index<source.getAvailableRows();index++)
	     {	 
			 writer.element("tr");
			 
			 Object obj = source.getRowValue(index);
			 
	    	 for (String name: (List<String>) model.getPropertyNames())
	    	 {
	    		 PropertyConduit conduit = model.get(name).getConduit();
	    		 
	    		 Class type = conduit.getPropertyType();
	    		 
	    		 String cellValue;
	    		 
			 	 Object val = conduit.get(obj);
			 	 
	    		 if(val == null) cellValue="";
	    		 else {
	    			 
	    			 try{
	    				 
	    				 if(type.equals(Date.class)){
	    					 Date cellDate = (Date)val;
		    			 	 
	    					 DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, request.getLocale());
		    			 	 
	    					 cellValue = dateFormat.format(cellDate) ;
	    				}
	    				 else if(type.equals(Enum.class)){
	    					 //TODO
	    					 cellValue="Not yet";
	    				 }
	    				 else {
	    					 cellValue = typeCoercer.coerce(val, String.class);
	    				 }
	    				 
	    			 }
	    			 catch (NullPointerException ex){
	    				 
	    				 cellValue = "undefined " + name;
	    				 
		    	     }
	    		 }
	    		 
	    		 writer.element("td");
	    		 writer.write(cellValue);
	    		 writer.end();
	    	 }	 
	    	 
	    	 writer.end();
	     }
		 
		
		 
		 
	 }
	 
	 
	 writer.end();//</tbody>

	 writer.end();//</table>
 
	 JSONObject setup = new JSONObject();
	 setup.put("id", clientId);
 
     
	 JSONObject dataTableParams = new JSONObject();
	 
	 if(mode) {
		 dataTableParams.put("sAjaxSource", resources.createEventLink("data").toAbsoluteURI());
		 dataTableParams.put("bServerSide", "true");
		 dataTableParams.put("bProcessing", "true");
	 }
	 
	 dataTableParams.put("sPaginationType", "full_numbers");
	 dataTableParams.put("iDisplayLength", getRowsPerPage());
	 dataTableParams.put("aLengthMenu", new JSONLiteral("[["+rowsPerPage+","+(rowsPerPage*2)+","+(rowsPerPage*4)+"],["+rowsPerPage+","+(rowsPerPage*2)+","+(rowsPerPage*4)+"]]"));
	 
	 
	 JQueryUtils.merge(dataTableParams, options);
	 
	 setup.put("params",dataTableParams);
	 
	 if (request.getAttribute(LOCALIZATION_CONFIGURED_FLAG) == null)
	 {
	     JSONObject spec = new JSONObject();
	     request.setAttribute(LOCALIZATION_CONFIGURED_FLAG, true);
	 }

	 support.addInitializerCall("dataTable", setup);
 }

 public String getClientId() {

     if (clientId == null) {

         clientId = javaScriptSupport.allocateClientId(resources);
     }

     return clientId;
 }

 /**
  * Defines where block and label overrides are obtained from. By default, the Grid component provides block
  * overrides (from its block parameters).
  */
 @Parameter(value = "this", allowNull = false)
 @Property(write = false)
 private PropertyOverrides overrides;
 
 /**
  * A version of GridDataSource that caches the availableRows property. This addresses TAPESTRY-2245.
  */
 static class CachingDataSource implements GridDataSource
 {
     private final GridDataSource delegate;

     private boolean availableRowsCached;

     private int availableRows;

     CachingDataSource(GridDataSource delegate)
     {
         this.delegate = delegate;
     }

     public int getAvailableRows()
     {
         if (!availableRowsCached)
         {
             availableRows = delegate.getAvailableRows();
             availableRowsCached = true;
         }

         return availableRows;
     }

     public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints)
     {
         delegate.prepare(startIndex, endIndex, sortConstraints);
     }

     public Object getRowValue(int index)
     {
         return delegate.getRowValue(index);
     }

     public Class getRowType()
     {
         return delegate.getRowType();
     }
 }

 /**
  * Default implementation that only allows a single column to be the sort column, and stores the sort information as
  * persistent fields of the Grid component.
  */
 class DefaultGridSortModel implements GridSortModel
 {
     public ColumnSort getColumnSort(String columnId)
     {
         if (!TapestryInternalUtils.isEqual(columnId, sortColumnId))
             return ColumnSort.UNSORTED;

         return getColumnSort();
     }

     private ColumnSort getColumnSort()
     {
         return getSortAscending() ? ColumnSort.ASCENDING : ColumnSort.DESCENDING;
     }

     public void updateSort(String columnId)
     {
         assert InternalUtils.isNonBlank(columnId);
         if (columnId.equals(sortColumnId))
         {
             setSortAscending(!getSortAscending());
             return;
         }

         sortColumnId = columnId;
         setSortAscending(true);
     }

     public List<SortConstraint> getSortConstraints()
     {
         if (sortColumnId == null)
             return Collections.emptyList();

         PropertyModel sortModel = getDataModel().getById(sortColumnId);

         SortConstraint constraint = new SortConstraint(sortModel, getColumnSort());

         return Collections.singletonList(constraint);
     }

     public void clear()
     {
         sortColumnId = null;
     }
 }

 GridSortModel defaultSortModel()
 {
     return new DefaultGridSortModel();
 }

 /**
  * Returns a {@link org.apache.tapestry5.Binding} instance that attempts to identify the model from the source
  * parameter (via {@link org.apache.tapestry5.grid.GridDataSource#getRowType()}. Subclasses may override to provide
  * a different mechanism. The returning binding is variant (not invariant).
  * 
  * @see BeanModelSource#createDisplayModel(Class, org.apache.tapestry5.ioc.Messages)
  */
 protected Binding defaultModel()
 {
     return new AbstractBinding()
     {
         public Object get()
         {
             // Get the default row type from the data source

             GridDataSource gridDataSource = (GridDataSource)source;

             Class rowType = gridDataSource.getRowType();

             if (rowType == null)
                 throw new RuntimeException(
                         String.format(
                                 "Unable to determine the bean type for rows from %s. You should bind the model parameter explicitly.",
                                 gridDataSource));

             // Properties do not have to be read/write

             return modelSource.createDisplayModel(rowType, overrides.getOverrideMessages());
         }

         /**
          * Returns false. This may be overkill, but it basically exists because the model is
          * inherently mutable and therefore may contain client-specific state and needs to be
          * discarded at the end of the request. If the model were immutable, then we could leave
          * invariant as true.
          */
         @Override
         public boolean isInvariant()
         {
             return false;
         }
     };
 }


 
 

 public BeanModel getDataModel()
 {
     if (dataModel == null)
     {
         dataModel = model;

         BeanModelUtils.modify(dataModel, add, include, exclude, reorder);
     }

     return dataModel;
 }

 

 public GridSortModel getSortModel()
 {
     return sortModel;
 }
 private boolean getSortAscending()
 {
     return sortAscending != null && sortAscending.booleanValue();
 }

 private void setSortAscending(boolean sortAscending)
 {
     this.sortAscending = sortAscending;
 }

 public int getRowsPerPage()
 {
     return rowsPerPage;
 }
 
}


