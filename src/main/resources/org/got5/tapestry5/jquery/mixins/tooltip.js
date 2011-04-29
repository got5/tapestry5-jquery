(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        tooltip: function(specs){
            $("#" + specs.id).tooltip(specs.params);
        }
    });
})(jQuery);