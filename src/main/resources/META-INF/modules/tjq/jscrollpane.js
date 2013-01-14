define(["vendor/jscrollpane"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).jScrollPane(spec.params);
	  };
  	
  	return exports = init;
});