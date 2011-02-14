(function( $ ) {

$.extend(Tapestry.Initializer, {
    tabs: function(specs) {
        $("#" + specs.id).tabs(specs.params);
        $( "#" + specs.id ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html(
						"Couldn't load this tab. We'll try to fix this as soon as possible. " +
						"If this wouldn't be a demo." );
				}
			}
		});
    }
});

}) ( jQuery );






