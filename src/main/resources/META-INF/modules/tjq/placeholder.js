define(["vendor/placeholder"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).placeholder();
	  };
  	
  	return exports = init;
});