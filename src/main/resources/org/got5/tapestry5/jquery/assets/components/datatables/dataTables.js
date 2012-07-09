(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
    	dataTable: function(specs){
    		
    		$.extend(specs.params,{
    			/**
    			 * For ajax mode, need to call Tapestry.loadScriptsInReply in a callback to take into account
    			 * propertyOverrides rendered in server-side
    			 * */
    			fnDrawCallback: function( oSettings ) {
    				if(oSettings.jqXHR){
	    				json = {};
	    				json.scripts = oSettings.jqXHR.responseText.evalJSON().scripts;
	    				json.stylesheets = oSettings.jqXHR.responseText.evalJSON().stylesheets;
	    				json.inits = oSettings.jqXHR.responseText.evalJSON().inits;
	    				Tapestry.loadScriptsInReply(json,function(){});
    				}
        	      }
    		});
    		
        	$("#" + specs.id).dataTable(specs.params);
        }
    });
})(jQuery);