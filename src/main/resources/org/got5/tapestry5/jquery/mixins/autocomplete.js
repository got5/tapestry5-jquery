(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        autocomplete: function(specs){
            conf = {
                source: function(request, response){
                    ajaxRequest = {
                        url: specs.url + "?" + specs.paramName + "=" + request.term,
                        success: function(data){
                            response(eval(data));
                        }
                    }
                    
                    $.ajax(ajaxRequest);
                }
            }
            
            if (specs.delay) 
                conf.delay = specs.delay;
            
            if (specs.minLength) 
                conf.minLength = specs.minLength
            
            $("#" + specs.id).autocomplete(conf);
        }
    });
})(jQuery);

