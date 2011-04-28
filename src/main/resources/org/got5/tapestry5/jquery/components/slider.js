(function( $ ) {
$.extend(Tapestry.Initializer, {
    slider: function(specs) {
		if(!specs.params.change)
			specs.params.change=function(){
				$("#" + specs.id).val($("#" + specs.id+"-slider").slider("value"));
			};
        $("#" + specs.id+"-slider").slider(specs.params);	
    }
});
}) ( jQuery );