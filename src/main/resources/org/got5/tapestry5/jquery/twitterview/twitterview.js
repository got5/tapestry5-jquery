(function( $ ) {
	$.extend(Tapestry.Initializer, {
	    twitterView: function(specs) {
	        $("#" + specs.id).twitterView(specs.params);
	    }
	});
}) ( jQuery );

