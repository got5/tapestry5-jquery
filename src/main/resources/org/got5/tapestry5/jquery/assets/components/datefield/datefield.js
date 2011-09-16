(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			$("#" + specs.field).datepicker({
                gotoCurrent: true,
                showOn: "button",
        		buttonImageOnly: false
            });
		}
		
		return {
			dateField : init
		}
	});
	
	$.extend(Tapestry, {
		DateField : {
			firstDay: 0,
			localized:false,
			initLocalization : function(loc) {
				this.months = loc.months;
				this.days = loc.days;
				this.firstDay = loc.firstDay;
			}
		}
	});
}) ( jQuery );