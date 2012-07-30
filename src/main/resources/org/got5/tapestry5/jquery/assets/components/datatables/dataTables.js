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
	    				json.scripts = $.evalJSON(oSettings.jqXHR.responseText).scripts;
	    				json.stylesheets = $.evalJSON(oSettings.jqXHR.responseText).stylesheets;
	    				json.inits = $.evalJSON(oSettings.jqXHR.responseText).inits;
	    				
	    				if(Tapestry.JQUERY){
	    					$.tapestry.utils.loadScriptsInReply(json,function(){});
	    				}
	    				else Tapestry.loadScriptsInReply(json,function(){});
    				}
        	      }
    		});
    		
        	$("#" + specs.id).dataTable(specs.params);
        }
    });
})(jQuery);