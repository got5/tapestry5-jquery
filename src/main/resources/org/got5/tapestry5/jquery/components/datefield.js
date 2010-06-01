(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        dateField: function(specs) {
			if (specs.localization && specs.localization.firstDay) {
				$.tapestry.dateField.firstDay = specs.localization.firstDay;
			}
			
            $("#" + specs.field).datepicker({
                firstDay: $.tapestry.dateField.firstDay,
                gotoCurrent: true
            });
        }
    });
	
	$.extend($.tapestry, {
		dateField : {
			firstDay: 0
		}
	});
})(jQuery);






