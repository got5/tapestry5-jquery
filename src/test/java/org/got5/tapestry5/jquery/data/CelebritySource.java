package org.got5.tapestry5.jquery.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.grid.ColumnSort;
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
        
        //this.cgds.prepare(indexFrom, indexTo, sortConstraints);
        
        for (SortConstraint constraint : sortConstraints)
        {
            final ColumnSort sort = constraint.getColumnSort();

            final PropertyConduit conduit = constraint.getPropertyModel().getConduit();

            if (sort == ColumnSort.UNSORTED || conduit == null)
                continue;

            final Comparator valueComparator = new Comparator<Comparable>()
            {
                public int compare(Comparable o1, Comparable o2)
                {
                    // Simplify comparison, and handle case where both are nulls.

                    if (o1 == o2)
                        return 0;

                    if (o2 == null)
                        return 1;

                    if (o1 == null)
                        return -1;

                    return o1.compareTo(o2);
                }
            };

            final Comparator rowComparator = new Comparator()
            {
                public int compare(Object row1, Object row2)
                {
                    Comparable value1 = (Comparable) conduit.get(row1);
                    Comparable value2 = (Comparable) conduit.get(row2);

                    return valueComparator.compare(value1, value2);
                }
            };

            final Comparator reverseComparator = new Comparator()
            {
                public int compare(Object o1, Object o2)
                {
                    int modifier = sort == ColumnSort.ASCENDING ? 1 : -1;

                    return modifier * rowComparator.compare(o1, o2);
                }
            };

            // We can freely sort this list because its just a copy.

            Collections.sort(this.celebrities, reverseComparator);
            this.cgds = new CollectionGridDataSource(this.celebrities);
        }

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

