(function($){
	   $.extend(Tapestry.Initializer, {
	     launchEvent: function(specs){
	    	 	$(specs.elementId).trigger(specs.event, specs.params);
	        }
	   });
})(jQuery);