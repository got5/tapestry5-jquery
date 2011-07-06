(function($){
	$.extend(Tapestry.Initializer, {
		progressiveDisplay:function(spec){
			$("#"+spec.element).tapestryZone();
			$("#"+spec.element).tapestryZone("update", spec);
		}
	});
})(jQuery)