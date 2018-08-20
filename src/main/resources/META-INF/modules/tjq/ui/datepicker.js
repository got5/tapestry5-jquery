requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/datepickerer": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/datepicker"], function(dom, events) {
	
	var datepicker = function(spec) {
		jQuery("#" + spec.field).datepicker({
            gotoCurrent: true,
            showOn: "button",
            buttonImageOnly: false,
            disabled: $("#" + spec.field).attr("disabled")
        });
	};

	
	return {
		// Widgets
		datepicker : datepicker
	};
});