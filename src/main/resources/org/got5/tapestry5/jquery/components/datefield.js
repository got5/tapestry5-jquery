(function($){
    
	
	/** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        dateField: function(specs) {	
			
            $("#" + specs.field).datepicker({
                firstDay: Tapestry.DateField.firstDay,
                gotoCurrent: true
            });
        }
    });
	
	$.extend(Tapestry, {
		DateField : {
			firstDay: 0,
			localized:false,
			initLocalization : function(localization) {
			
				this.months = localization.months;
				this.days = localization.days;
				this.firstDay = localization.firstDay;
			
				//set the locale
				$.datepicker.setDefaults( $.datepicker.regional[ "" ] );
				$( "#datepicker" ).datepicker( $.datepicker.regional[ localization.language ] );
				$( "#locale" ).change(function() {
					$( "#datepicker" ).datepicker( "option",
													$.datepicker.regional[ $( this ).val() ] );
				});
			
			}
		}
	});
			
})(jQuery);






