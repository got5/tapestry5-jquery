(function() {
    var locale = (document.documentElement.getAttribute("data-locale")) || "en";	
    requirejs.config({
        paths: {
            "datepickerLocal" : "tjq/vendor/ui/i18n/jquery.ui.datepicker-"+locale
        },
        shim : {
            "tjq/vendor/ui/jquery.ui.datepicker": ["jquery"],
            "tjq/vendor/ui/jquery-ui.custom": ["tjq/vendor/ui/jquery.ui.datepicker"],
            "datepickerLocal": {
                deps: ["tjq/vendor/ui/jquery-ui.custom","tjq/vendor/ui/jquery.ui.datepicker"]
            }
        }
    });
	 
    define(["t5/core/dom", "t5/core/events","tjq/vendor/ui/jquery.ui.datepicker", "tjq/vendor/ui/jquery-ui.custom","datepickerLocal"], function() {
        return function(spec) {

            jQuery.datepicker.setDefaults(jQuery.datepicker.regional[spec.locale]);
            jQuery(spec.selector).datepicker("option", spec.params);
        };
    });
}).call(this);
