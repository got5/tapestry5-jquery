requirejs.config({
	"shim" : {
		"tjq/vendor/mixins/placeholder/jquery.placeholder": ["jquery"]
	}
});
define(["tjq/vendor/mixins/placeholder/jquery.placeholder"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).placeholder();
	  };
  	
  	return exports = init;
});