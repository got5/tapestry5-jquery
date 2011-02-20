(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        button: function(specs){
            if(specs.type=="buttonset")
            	 $("#" + specs.id).buttonset(specs.params);
            else
            	$("#" + specs.id).button(specs.params);
        }
    });
})(jQuery);

