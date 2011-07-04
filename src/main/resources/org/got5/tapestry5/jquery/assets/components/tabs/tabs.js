(function( $ ) {
	$.extend(Tapestry.Initializer, {
	    tabs: function(specs) {
	        $("#" + specs.id).tabs(specs.params);
	    }
	});
}) ( jQuery );