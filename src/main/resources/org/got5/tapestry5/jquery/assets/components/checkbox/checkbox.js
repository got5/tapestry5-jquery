(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        checkbox: function(specs){
            $("#" + specs.id).checkbox();
        }
    });
})(jQuery);