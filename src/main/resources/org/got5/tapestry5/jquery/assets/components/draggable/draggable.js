(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        draggable: function(specs){
    		$( "#" + specs.id ).draggable(specs.params).data("contexte",specs.context);
        }
    });
})(jQuery);

