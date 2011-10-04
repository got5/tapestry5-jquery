(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        editable: function(specs){
            $("#" + specs.clientId).editable(specs.href,specs.options);
        }
    });
})(jQuery);