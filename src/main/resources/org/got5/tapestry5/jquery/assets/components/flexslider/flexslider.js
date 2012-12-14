(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        flexslider: function(specs){
    		$( "#" + specs.id ).flexslider(specs.params);
        }
    });
})(jQuery);

