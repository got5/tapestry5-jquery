requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/accordion": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/accordion"], function(dom, events) {

	var accordion = function(spec) {
		jQuery("#" + spec.id).accordion(spec.params);
	};

	
	return {
		// Widgets
		accordion : accordion
	
	};
});