(function($){
	$.extend(Tapestry.Initializer, {
		progressiveDisplay:function(spec){
			$("#"+spec.element).tapestryZone(spec).tapestryZone("update", spec);
		}
	});
})(jQuery)