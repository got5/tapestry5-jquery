(function( $ ) {

$.extend(Tapestry.Initializer, {
	reveal: function(specs) {
		
		$('#'+specs.div).addClass('reveal-modal');
		
    	$('#'+specs.id).click(function(e) {
            e.preventDefault();
            $('#'+specs.div).reveal({
            	animation:specs.animation,
            	animationspeed:specs.animationspeed,
            	closeonbackgroundclick:specs.closeonbackgroundclick,
            	dismissmodalclass:specs.dismissmodalclass
            });
    	});
    }
});

}) ( jQuery );

