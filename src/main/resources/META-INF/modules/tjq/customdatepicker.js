(function() {
		 locale = (document.documentElement.getAttribute("data-locale")) || "en";	
		 var datepickerLocal= "tjq/vendor/ui/i18n/jquery.ui.datepicker-"+locale;

define(["t5/core/dom", "t5/core/events","tjq/vendor/ui/jquery.ui.datepicker", "tjq/vendor/ui/jquery-ui.custom",datepickerLocal], function() {
	init = function(spec) {
		
		jQuery.datepicker.setDefaults(jQuery.datepicker.regional[spec.locale]);
        jQuery(spec.selector).datepicker("option", spec.params);
	};
  	
  	return exports = init;
});
}).call(this);