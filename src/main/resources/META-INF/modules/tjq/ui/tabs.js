requirejs.config({
	"shim" : {
		"tjq/vendor/jquery.json-2.4": ["jquery"] ,
		"tjq/vendor/ui/widgets/tabs": ["jquery"]
	}
});
define([  "t5/core/dom", "t5/core/events", "jquery", "tjq/vendor/jquery.json-2.4", "tjq/vendor/ui/widgets/tabs"], function(dom, events) {

	var tabs = function(spec) {
		var p = spec.params;
		if (!p.ajaxOptions)
			p.ajaxOptions = {};
		if (!p.ajaxOptions.beforeSend)
			jQuery.extend(p.ajaxOptions, {
				beforeSend : function() {
					// returning false in beforeSend function cancels the AJAX
					// request, see issue #52
					return false;
				}
			});
		jQuery("#" + spec.id).tabs(p);
	};


	return {
		// Widgets
		tabs : tabs
	};
});