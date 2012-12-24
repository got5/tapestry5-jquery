define(["vendor/jqueryui"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).tooltip(spec.options);
	  };
  	
  	return exports = init;
});