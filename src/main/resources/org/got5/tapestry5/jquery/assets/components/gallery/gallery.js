(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        gallery : function(spec){
    		$(spec.selector).colorbox(spec);
        }
    });
})(jQuery);

