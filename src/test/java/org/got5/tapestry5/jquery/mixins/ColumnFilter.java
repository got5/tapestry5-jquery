package org.got5.tapestry5.jquery.mixins;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.MixinAfter;

@MixinAfter
public class ColumnFilter {
	
	public void afterRender(MarkupWriter writer){
		writer.getElement().forceAttributes("disabled", "disabled");
		
		/*JSONObject opt = new JSONObject();
		opt.put("id", table.getClientId());
		opt.put("options", filterJson);
		
		js.addInitializerCall(InitializationPriority.LATE, "columnFilter", opt);
		js.addInitializerCall(InitializationPriority.EARLY, "addHeader", opt);*/
	}
	
}
