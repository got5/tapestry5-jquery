(function( $ ) {
$.extend(Tapestry.Initializer, {
    rangeSlider: function(specs) {
		if(!specs.displayTextField) {
			$("#" + specs.idMinField).css("display", "none");
			$("#" + specs.idMaxField).css("display", "none");
		}
		var options={
			slide:function(e,u){
				$("#" + specs.idMinField).val(u.values[0]);
				$("#" + specs.idMaxField).val(u.values[1]);
			}, 
			change:function(e,u){
				if(specs.url) 
					$("#" + specs.zoneId).tapestryZone("update", {
						url: specs.url+"?min="+u.values[0]+"&max="+u.values[1]
					});
			}
		};
        $("#" + specs.sliderId).slider(specs.params).slider("option", options);	
    }
});
}) ( jQuery );