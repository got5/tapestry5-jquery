requirejs.config({
	"shim" : {
		"tjq/vendor/ui/jquery-ui.custoom" : ["jquery"],
		"tjq/vendor/jquery.json-2.4" : ["jquery"],
		"tjq/vendor/components/palette/jquery.palette": ["tjq/vendor/ui/jquery-ui.custom", "tjq/vendor/jquery.json-2.4"]
	}
});
define(["tjq/vendor/components/palette/jquery.palette"], function() {
	init = function(specs) {
		jQuery("#" + specs.id).palette(specs);
	  };
  	
  	return exports = init;
});

