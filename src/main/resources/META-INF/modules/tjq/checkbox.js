requirejs.config({
	"shim" : {
		"tjq/ckbox/jquery.ui.checkbox": ["vendor/jqueryui"]
	}
});

define(["tjq/ckbox/jquery.ui.checkbox"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).checkbox();
	  };
  	
  	return exports = init;
});