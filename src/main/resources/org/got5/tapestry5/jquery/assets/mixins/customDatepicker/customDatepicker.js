(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
    	customDatepicker: function(specs){
    		$("#" + specs.id).datepicker('option',specs.params);
        }
    });
})(jQuery);