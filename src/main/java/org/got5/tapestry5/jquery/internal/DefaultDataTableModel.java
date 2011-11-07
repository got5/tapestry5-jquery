package org.got5.tapestry5.jquery.internal;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.grid.CollectionGridDataSource;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.got5.tapestry5.jquery.DataTableConstants;

/**
 * TODO
 */
public class DefaultDataTableModel implements DataTableModel {

	private TypeCoercer typeCoercer;
	
	private Request request;
	
	private GridSortModel sortModel;
	
	private BeanModel model;
	
	private PropertyOverrides overrides;
	
	private JSONObject response = new JSONObject();
	public DefaultDataTableModel(TypeCoercer typeCoercer) {
		super();
		this.typeCoercer = typeCoercer;
	}


	/**
	 * TODO
	 */
	public GridDataSource filterData(GridDataSource source){
		
		
		final List<Object> datas = new ArrayList<Object>();
	    
		for(int index=0;index<source.getAvailableRows();index++){
			
			boolean flag = false;
			
			for (Object name: model.getPropertyNames())
	    	{
				PropertyConduit conduit = model.get((String) name).getConduit();
	    		
				Class type = conduit.getPropertyType();
				
				try{
					
					String val = (String) conduit.get(source.getRowValue(index));
					
					if(val.contains(request.getParameter(DataTableConstants.SEARCH)))
						flag = true;
				}
				catch (Exception e){
					
				}
	    	}
			
			if(flag){
				
				datas.add(source.getRowValue(index));
			}
			
		}
		
		
		return new GridDataSource() {
			
			private CollectionGridDataSource cgds = new CollectionGridDataSource(datas);

			public void prepare(int startIndex, int endIndex,
					List<SortConstraint> sortConstraints) {

				this.cgds.prepare(startIndex, endIndex, sortConstraints);
			}
			
			public Object getRowValue(int index) {
				return this.cgds.getRowValue(index);
			}
			
			public Class getRowType() {
				return this.cgds .getRowType();
			}
			
			public int getAvailableRows() {
				return this.cgds.getAvailableRows();
			}
		};
		
	}
	
	/**
	 * TODO
	 */
	public void prepareResponse(GridDataSource source){
		
		String sortingCols = request.getParameter(DataTableConstants.SORTING_COLS);
	    
		int nbSortingCols = Integer.parseInt(sortingCols);
	    
		String sord = request.getParameter(DataTableConstants.SORT_DIR+"0");
	    
		String sidx = request.getParameter(DataTableConstants.SORT_COL+"0");
	     
	    if(nbSortingCols>0)
	    {
	    	List<String> names = model.getPropertyNames();
	    	
	    	int indexProperty = Integer.parseInt(sidx);
	    	
	    	String propName = names.get(indexProperty);
	    	 
	    	ColumnSort colSort =sortModel.getColumnSort(propName);
	    	 
	    	sortModel.updateSort(propName);
	    }
	     
	}
	
	/**
	 * TODO
	 */
	public JSONObject getResponse(GridDataSource source){
		
		response.put("sEcho", request.getParameter(DataTableConstants.ECHO));
		
		int records = source.getAvailableRows();
	    
		response.put("iTotalDisplayRecords", records);  
	    
		response.put("iTotalRecords", records);
	    
	    String displayStart = request.getParameter(DataTableConstants.DISPLAY_START);
	     int startIndex=Integer.parseInt(displayStart);
	     
	     String displayLength = request.getParameter(DataTableConstants.DISPLAY_LENGTH);
	     
	     int rowsPerPage=Integer.parseInt(displayLength);
	     
	     int endIndex= startIndex + rowsPerPage -1;
	     if(endIndex>records-1) endIndex= records-1;
	     source.prepare(startIndex,endIndex + rowsPerPage,sortModel.getSortConstraints() );
	     
	     
	     JSONArray rows = new JSONArray();
	     
	     for(int index=startIndex;index<=endIndex;index++)
	     {	 
	    	 JSONArray cell = new JSONArray();
	    	 
	    	 Object obj = source.getRowValue(index);
	    	 
	    	 List<String> names = model.getPropertyNames();
	    	 
	    	 for (String name: names)
	    	 {
	    		 PropertyConduit conduit = model.get(name).getConduit();
	    		 
	    		 Class type = conduit.getPropertyType();
	    		 
	    		 String cellValue;
	    		 
			 	 Object val = conduit.get(obj);
			 	 
	    		 if(val == null) cell.put("");
	    		 else {
	    			 
	    			 try{
	    				 
	    				 if(type.equals(Date.class)){
	    					 Date cellDate = (Date)val;
		    			 	 
	    					 DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, request.getLocale());
		    			 	 
	    					 cellValue = dateFormat.format(cellDate) ;
	    				}
	    				 else if(type.equals(Enum.class)){
	    					 cellValue=TapestryInternalUtils.getLabelForEnum(overrides.getOverrideMessages(), (Enum) val);
	    				 }
	    				 else {
	    					 cellValue = typeCoercer.coerce(val, String.class);
	    				 }
	    				 
	    			 }
	    			 catch (NullPointerException ex){
	    				 
	    				 cellValue = "undefined " + name;
	    				 
		    	     }
	    			
	    			 cell.put(cellValue);
	    		 }
	    	 }	 
	    	 
	    	 rows.put(cell);
	     }
	    
		response.put("aaData", rows);
		
		return response;
	}
	
	/**
	 * TODO
	 */
	public JSONObject sendResponse(Request request, GridDataSource source, BeanModel model, GridSortModel sortModel, PropertyOverrides overrides) {
		
		this.request = request;
		this.sortModel = sortModel;
		this.model = model;
		this.overrides = overrides;
		
		GridDataSource s = source;
		
		if(InternalUtils.isNonBlank(request.getParameter(DataTableConstants.SEARCH))) s = filterData(source);
		
		prepareResponse(s);
		
		return getResponse(s);
	     
	}

}
