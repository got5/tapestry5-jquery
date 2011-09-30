(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			var conf = {
					source: function(request, response){
	                    var ajaxRequest = {
	                    	url:specs.url,
	                        success: function(data){
	                            response(eval(data));
	                        }, 
	                        data:specs.paramName+"="+request.term, 
	                        type:"POST"
	                    };
	                    $.ajax(ajaxRequest);
	                }
	        };
	        if (specs.delay) 
	        	conf.delay = specs.delay;
	            
	        if (specs.minLength) 
	        	conf.minLength = specs.minLength;
	            
	        $("#" + specs.id).autocomplete(conf);
	    }
		
		return {
			autocomplete : init
		}
	});
	
}) ( jQuery );