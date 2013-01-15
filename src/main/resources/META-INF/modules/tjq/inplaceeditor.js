define(["vendor/jeditable"], function() {
	init = function(spec) {
	     jQuery("#" + spec.clientId).editable(spec.href,spec.options);
	  };
  	
  	return exports = init;
});