(function($){
	$.extend(Tapestry.Initializer, {
		
		raty: function(specs) {
			
			if(specs.location == "before")
			{
				$("#" + specs.target).before("<div id='" + specs.id + "'/>");
			}
			else
			{
				$("#" + specs.target).after("<div id='" + specs.id + "'/>");
			}
			
			if(specs.hide){ $("#" + specs.target).hide(); }
			
			$("#" + specs.id).raty(specs.params);
			
		}
		
	});
})(jQuery);