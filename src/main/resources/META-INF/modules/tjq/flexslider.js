define(["vendor/flexslider"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).flexslider(spec.params);
	  };
  	
  	return exports = init;
});