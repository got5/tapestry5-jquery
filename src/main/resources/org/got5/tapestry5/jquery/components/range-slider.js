(function( $ ) {
$.extend(Tapestry.Initializer, {
    rangeSlider: function(specs) {
		var options={
			slide:function(e,u){
				$("#" + specs.id+"-min-field").val(u.values[0]);
				$("#" + specs.id+"-max-field").val(u.values[1]);
			}, 
			change:function(e,u){
				if(specs.url) 
					$("#" + specs.zoneId).tapestryZone("update", {
						url: specs.url+"?min="+u.values[0]+"&max="+u.values[1]
					});
			}
		};
        $("#" + specs.id+"-slider").slider(specs.params).slider("option", options);	
    }
});
}) ( jQuery );