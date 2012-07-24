(function($){
	$.extend(Tapestry.Initializer, {
		gmap: function(specs) {
			$("#" + specs.id).gmap3(specs.params);
        }
	});
})(jQuery);