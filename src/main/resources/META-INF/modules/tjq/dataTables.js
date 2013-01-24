define(["t5/core/pageinit", "vendor/datatables", "vendor/jqueryjson"], function(pageinit) {
	init = function(spec) {
		jQuery.extend(spec.params,{
    			/**
    			 * For ajax mode, need to call Tapestry.loadScriptsInReply in a callback to take into account
    			 * propertyOverrides rendered in server-side
    			 * */
    			fnDrawCallback: function( oSettings ) {
    				if(oSettings.jqXHR){
    					json = {
    						json: jQuery.evalJSON(oSettings.jqXHR.responseText)
    					};
    					
    					pageinit.handlePartialPageRenderResponse(json, function(){});
	    				/*json = {};
	    				json.scripts = jQuery.evalJSON(oSettings.jqXHR.responseText).scripts;
	    				json.stylesheets = jQuery.evalJSON(oSettings.jqXHR.responseText).stylesheets;
	    				json.inits = jQuery.evalJSON(oSettings.jqXHR.responseText).inits;
	    				
	    				if(Tapestry.JQUERY){
	    					jQuery.tapestry.utils.loadScriptsInReply(json,function(){});
	    				}
	    				else Tapestry.loadScriptsInReply(json,function(){});*/
    				}
        	      }
    		});
    		
			 jQuery("#" + spec.id).dataTable(spec.params);
	  };
  	
  	return exports = init;
});