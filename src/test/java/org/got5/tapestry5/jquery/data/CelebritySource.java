package org.got5.tapestry5.jquery.data;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.grid.CollectionGridDataSource;

public class CelebritySource implements GridDataSource {

	private IDataSource dataSource;
    private List<Celebrity> celebrities;
    private CollectionGridDataSource cgds;

  
    
    public CelebritySource(IDataSource ds) {
        this.dataSource = ds;
        this.celebrities = dataSource.getAllCelebrities();
        this.cgds = new CollectionGridDataSource(dataSource.getAllCelebrities());
  
    }

    public int getAvailableRows() {
        //return dataSource.getAllCelebrities().size();
    	//return celebrities.size();
    	return this.cgds.getAvailableRows();
    }

    public void prepare(int indexFrom, int indexTo,List<SortConstraint> sortConstraints) {
        System.out.println("Preparing selection.");
        System.out.println("Index from " + indexFrom + 
          " to " + indexTo);
        
        this.cgds.prepare(indexFrom, indexTo, sortConstraints);
    }

    public Object getRowValue(int i) {
        System.out.println("Getting value for row " + i);
        return this.cgds.getRowValue(i);
    }

    public Class getRowType() {
    	return this.cgds.getRowType();
    }
    
    public void resetFilter()
    {
    	this.cgds = new CollectionGridDataSource(dataSource.getAllCelebrities());
    }
    
	
	
}
