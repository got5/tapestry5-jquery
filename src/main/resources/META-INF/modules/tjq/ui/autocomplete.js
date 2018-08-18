requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/autocomplete": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/autocomplete"], function(dom, events) {

	var autocomplete = function(spec) {
		var conf = {
				source: function(request, response){
					
					var params = {};
					
					var extra = jQuery("#" + spec.id).data('extra');
					if(extra) {
						params["extra"] = extra;
					}
					
					params[spec.paramName] = request.term;
					
					var ajaxRequest = {
					 	type:"POST",
                    	url:spec.url,
                    	dataType: "json",
        				success: function(data){
                            response(eval(data));
                        }, 
                        data:{
                        	"data" : jQuery.toJSON( params )
                        } 
                    };
                    this.xhr = jQuery.ajax(ajaxRequest);
                }
        };
        
        if (spec.delay >= 0) 
        	conf.delay = spec.delay;
            
        if (spec.minLength >= 0) 
        	conf.minLength = spec.minLength;

        if (spec.options) {
        	jQuery.extend(conf, spec.options);
        }
        
        jQuery("#" + spec.id).autocomplete(conf);
	};


	return {
		// Widgets
		autocomplete : autocomplete
	};
});