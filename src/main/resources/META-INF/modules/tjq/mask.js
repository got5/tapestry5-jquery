define(["vendor/mask"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).mask(spec.format);
	  };
  	
  	return exports = init;
});