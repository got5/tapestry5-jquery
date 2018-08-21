requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/slider": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/slider"], function(dom, events) {

	var slider = function(spec) {
		jQuery("#" + spec.id).slider(spec.params);
	};

	return {
		// Widgets
		slider : slider
	};	
	
});