requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/button": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/button"], function(dom, events) {

	var button = function(spec) {
		if (spec.type == "buttonset")
			jQuery("#" + spec.id).buttonset(spec.params);
		else
			jQuery("#" + spec.id).button(spec.params);
	};


	return {
		// Widgets
		button : button
	};
});