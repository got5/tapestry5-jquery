(function() {
    var locale = (document.documentElement.getAttribute("data-locale")) || "en";	
    var localeJS = locale.replace(/_/g, '-'); //converting local name from Java to JS  
    requirejs.config({
        paths: {
            "datepickerLocal" : "tjq/vendor/ui/i18n/jquery.ui.datepicker-"+localeJS
        },
        shim : {
            "tjq/vendor/ui/widgets/datepicker": ["jquery"],
            "tjq/vendor/ui/jquery-ui": ["tjq/vendor/ui/widgets/datepicker"],
            "datepickerLocal": {
                deps: ["tjq/vendor/ui/jquery-ui","tjq/vendor/ui/widgets/datepicker"]
            }
        }
    });
	 
    define(["datepickerLocal"], function() {
        return function(spec) {
        	jQuery(spec.selector).datepicker("option", spec.params);
        };
    });
}).call(this);
