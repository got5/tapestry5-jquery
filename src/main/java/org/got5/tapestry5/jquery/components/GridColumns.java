package org.got5.tapestry5.jquery.components;

import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @tapestrydoc
 */
@SupportsInformalParameters
public class GridColumns {
	
	/**
     * The object that provides access to bean and data models, which is typically the enclosing Grid component.
     */
    @Parameter
    private BeanModel model;

    /**
     * If true, then the CSS class on each &lt;TH&gt; element will be omitted, which can reduce the amount of output
     * from the component overall by a considerable amount. Leave this as false, the default, when you are leveraging
     * the CSS to customize the look and feel of particular columns.
     */
    @Parameter
    private boolean lean;

    /**
     * Where to look for informal parameter Blocks used to override column headers.  The default is to look for such
     * overrides in the GridColumns component itself, but this is usually overridden.
     */
    @Parameter("this")
    private PropertyOverrides overrides;
    
    /**
     * If not null, then each link is output as a link to update the specified zone.
     */
    @Parameter
    private String zone;

   @Parameter 
    private Boolean mode;
    
    @Inject
    private Messages messages;

    @Inject
    private Block standardHeader;
    
    @Inject
    private Block standardFooter;

    /**
     * Optional output parameter that stores the current column index.
     */
    @Parameter(cache=false)
    @Property
    private int index;

    /**
     * Caches the index of the last column.
     */
    private int lastColumnIndex;

    @Property(write = false)
    private PropertyModel columnModel;

    @Inject
    private ComponentResources resources;

    void setupRender()
    {
        lastColumnIndex = model.getPropertyNames().size() - 1;
    }

    public Object getColumnContext()
    {
        if (zone == null) return columnModel.getId();

        return new Object[] { columnModel.getId(), zone };
    }

    public List<String> getColumnNames()
    {
        return model.getPropertyNames();
    }


    public void setColumnName(String columnName)
    {
        columnModel = model.get(columnName);
    }


    public Block getBlockForColumn()
    {
        Block override = overrides.getOverrideBlock(columnModel.getId() + (mode ? "Header" : "Footer"));

        if (override != null) return override;

        return mode ? standardHeader : standardFooter;
    }
    
    public Boolean getMode(){return mode;}
}

