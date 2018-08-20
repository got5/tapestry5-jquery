requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/dialog": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/dialog"], function(dom, events) {

	var dialog = function(spec) {
		 jQuery("#" + spec.id).dialog(spec.params);
	};

	return {
		// Widgets
		dialog : dialog
	};
});