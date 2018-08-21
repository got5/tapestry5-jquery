requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/tooltip": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/tooltip"], function(dom, events) {

	var tooltip = function(spec) {
		jQuery("#" + spec.id).tooltip(spec.options);
	};

	return {
		// Widgets
		tooltip : tooltip
	};
});