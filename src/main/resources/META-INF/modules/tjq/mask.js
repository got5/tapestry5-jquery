requirejs.config({
	"shim" : {
		"tjq/vendor/mixins/mask/jquery-maskedinput": ["jquery"]
	}
});
define(["tjq/vendor/mixins/mask/jquery-maskedinput"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).mask(spec.format);
	  };
  	
  	return exports = init;
});