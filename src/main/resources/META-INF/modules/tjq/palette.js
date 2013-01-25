define(["vendor/palette"], function() {
	init = function(specs) {
		jQuery("#" + specs.id).palette(specs);
	  };
  	
  	return exports = init;
});

