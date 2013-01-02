define(["vendor/reveal"], function() {
	init = function(spec) {
		jQuery('#'+spec.div).addClass('reveal-modal');

		jQuery(spec.id).click(function(e) {
            e.preventDefault();
            jQuery('#'+spec.div).reveal({
                animation:spec.animation,
                animationspeed:spec.animationspeed,
                closeonbackgroundclick:spec.closeonbackgroundclick,
                dismissmodalclass:spec.dismissmodalclass
            });
        });
	  };
  	
  	return exports = init;
});
