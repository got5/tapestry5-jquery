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

import java.util.List;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.data.Celebrity;
import org.got5.tapestry5.jquery.data.IDataSource;

@Import(stylesheet = { "context:dataTables/css/demo_table_jui.css", "context:dataTables/css/demo_page.css",
                      "context:dataTables/css/demo_table.css", "context:dataTables/ColVis/media/css/ColVis.css",
                      "context:dataTables/ColReorder/media/css/ColReorder.css",
                      "context:dataTables/TableTools/media/css/TableTools.css" })
public class DataTablesAjaxSimple {

    @SessionState
    private IDataSource dataSource;

    private Celebrity   celebrity;

    @Property
    private Celebrity   current;

    @Property
    private int         index;

    public List<Celebrity> getAllCelebrities() {
        System.out.println("Getting all celebrities...");
        return this.dataSource.getAllCelebrities();
    }

    public Celebrity getCelebrity() {
        return this.celebrity;
    }

    public void setCelebrity(Celebrity celebrity) {
        this.celebrity = celebrity;
    }

    public JSONObject getOptions() {

        return new JSONObject("bJQueryUI", "true"/*, "sDom", "TC<\"clear\">Rlfrtip"*/);
    }

    /**
     * Event handler method called when datatable's search field is used
     * Gives a chance to the user to filter data in lazy-loading mode (mode=true)
     * */
    //    @OnEvent(value = JQueryEventConstants.FILTER_DATA, component = "datatableAjax")
    //    public void filterData() {
    //        String val = this.request.getParameter(DataTableConstants.SEARCH);
    //        this.dataSource.filter(val);
    //    }

}
