(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
    	dataTable: function(specs){
        	$("#" + specs.id).dataTable(specs.params);
        }
    });
})(jQuery);