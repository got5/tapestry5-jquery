(function() {
    var locale = (document.documentElement.getAttribute("data-locale")) || "en";	
    requirejs.config({
        paths: {
            "datepickerLocal" : "tjq/vendor/ui/i18n/jquery.ui.datepicker-"+locale
        },
        shim : {
            "tjq/vendor/ui/datepicker": ["jquery"],
            "tjq/vendor/ui/custom": ["tjq/vendor/ui/datepicker"],
            "datepickerLocal": {
                deps: ["tjq/vendor/ui/custom","tjq/vendor/ui/datepicker"]
            }
        }
    });
	 
    define(["datepickerLocal"], function() {
        return function(spec) {

            jQuery.datepicker.setDefaults(jQuery.datepicker.regional[spec.locale]);
            jQuery(spec.selector).datepicker("option", spec.params);
        };
    });
}).call(this);
