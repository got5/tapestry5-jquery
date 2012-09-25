(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        editable: function(specs){
			if(specs.options.callback!=undefined)
			{
				specs.options.callback= new Function("value", "settings",specs.options.callback);
			}
            $("#" + specs.clientId).editable(specs.href,specs.options);
        }
    });
})(jQuery);