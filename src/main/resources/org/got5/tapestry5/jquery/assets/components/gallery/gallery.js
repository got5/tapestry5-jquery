(function($) {

	$.extend(Tapestry.Initializer, {

		gallery : function(spec) 
		{
			$(spec.selector).colorbox(spec); 
		}

	});

})(jQuery);
