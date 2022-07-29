package org.got5.tapestry5.jquery.base;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.got5.tapestry5.jquery.data.Celebrity;
import org.got5.tapestry5.jquery.data.CelebritySource;
import org.got5.tapestry5.jquery.data.IDataSource;
import org.got5.tapestry5.jquery.internal.TableInformation;

import java.util.List;

@Import(stylesheet ={ "context:dataTables/css/demo_table_jui.css",
        "context:dataTables/css/demo_page.css",
        "context:dataTables/css/demo_table.css",
        "context:dataTables/ColVis/media/css/ColVis.css",
        "context:dataTables/ColReorder/media/css/ColReorder.css",
        "context:dataTables/TableTools/media/css/TableTools.css"})
public class BaseDataTables {

    @Property
    private Celebrity celebrity;

    @SessionState
    private IDataSource dataSource;

    private CelebritySource celebritySource;

    @Inject
    private BeanModelSource beanModelSource;

    @SuppressWarnings("unchecked")
    private BeanModel model;

    @Inject
    protected ComponentResources resources;

    public GridDataSource getCelebritySource() {
        if(celebritySource==null)
            celebritySource = new CelebritySource(dataSource);
        return celebritySource;
    }

    public BeanModel getModel() {
        this.model = beanModelSource.createDisplayModel(Celebrity.class,resources.getMessages());
        this.model.get("firstName").sortable(false);
        return model;
    }

    public List<Celebrity> getAllCelebrities() {
        return dataSource.getAllCelebrities();
    }

    public IDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TableInformation getInformation(){
        return new TableInformation() {

            public String getTableSummary() {
                // TODO Auto-generated method stub
                return "A summary description of table data";
            }

            public String getTableCaption() {
                // TODO Auto-generated method stub
                return "The table title";
            }

            public String getTableCSS() {
                // TODO Auto-generated method stub
                return "k-data-table";
            }
        };
    }
}
