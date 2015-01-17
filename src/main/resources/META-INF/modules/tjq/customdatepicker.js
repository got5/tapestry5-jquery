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
	 
    define(["datepickerLocal"], function() {
        return function(spec) {

            jQuery.datepicker.setDefaults(jQuery.datepicker.regional[spec.locale]);
            jQuery(spec.selector).datepicker("option", spec.params);
        };
    });
}).call(this);
