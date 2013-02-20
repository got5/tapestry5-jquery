requirejs.config({
	"shim" : {
		"tjq/vendor/mixins/reveal/jquery.reveal": ["jquery"]
	}
});
define(["tjq/vendor/mixins/reveal/jquery.reveal"], function() {
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
