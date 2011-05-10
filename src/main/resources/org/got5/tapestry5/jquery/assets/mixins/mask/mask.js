(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
    	mask: function(specs){
            $("#" + specs.id).mask(specs.format);
        }
    });
})(jQuery);