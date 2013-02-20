requirejs.config({
	"shim" : {
		"tjq/vendor/ui/jquery-ui.custom": ["jquery"],
		"tjq/vendor/ui/i18n/jquery-ui-i18n": ["tjq/vendor/ui/jquery-ui.custom"]
	}
});

define(["tjq/vendor/ui/i18n/jquery-ui-i18n"], function() {
	init = function(spec) {
		
		jQuery.datepicker.setDefaults(jQuery.datepicker.regional[spec.locale]);
		jQuery(spec.selector).data('cutomDatepicker',spec);	
	};
  	
  	return exports = init;
});