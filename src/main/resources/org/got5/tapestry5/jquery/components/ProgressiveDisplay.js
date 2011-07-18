(function($){
	$.extend(Tapestry.Initializer, {
		progressiveDisplay:function(spec){
			$("#"+spec.element).tapestryZone().tapestryZone("update", spec);
		}
	});
})(jQuery)