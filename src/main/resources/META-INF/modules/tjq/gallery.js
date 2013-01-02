define(["vendor/colorbox"], function() {
	init = function(spec) {
	    jQuery(spec.selector).colorbox(spec);
	  };
  	
  	return exports = init;
});