(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        autocomplete: function(specs){
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
    });
})(jQuery);