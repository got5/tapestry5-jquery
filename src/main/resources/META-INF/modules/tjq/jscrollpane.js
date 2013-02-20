requirejs.config({
	"shim" : {
		"tjq/vendor/mixins/jscrollpane/jquery.jscrollpane.min": ["jquery"]
	}
});
define(["tjq/vendor/mixins/jscrollpane/jquery.jscrollpane.min"], function() {
	init = function(spec) {
	    jQuery("#" + spec.id).jScrollPane(spec.params);
	  };
  	
  	return exports = init;
});