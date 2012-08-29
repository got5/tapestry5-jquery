(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {

			var element = $("#" + specs.id), 
			    conf = {
					source: function(request, response){

						var params = {};

						var extra = element.data('extra');
						if(extra) {
							params["extra"] = extra;
						}

						params[specs.paramName] = request.term;
						
						var ajaxRequest = {
	                    	url:specs.url,
	                        success: function(data){
	                            response(eval(data));
	                        }, 
	                        data:"data="+$.toJSON( params ), 
	                        dataType: "json", 
	                        type:"POST"
	                    };

	                    $.ajax(ajaxRequest).done(function () { element.trigger('autocompletedone'); });
	                }
	        };
	        if (specs.delay >= 0) 
	        	conf.delay = specs.delay;
	            
	        if (specs.minLength >= 0) 
	        	conf.minLength = specs.minLength;

	        if (specs.options) {
	            $.extend(conf, specs.options);
	        }
   
	        element.autocomplete(conf);
	    }
		
		return {
			autocomplete : init
		}
	});
	
}) ( jQuery );