(function($){
    
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        source: function(specs){
        	$("pre."+specs.lang).snippet(specs.lang,
        			{
        				style:specs.style,
        				collapse:specs.collapse,
        				showMsg:specs.showMsg,
        				hideMsg:specs.hideMsg
        			});
        }
    });
})(jQuery);