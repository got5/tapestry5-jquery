(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			var params = $.extend({
                gotoCurrent: true,
                showOn: "button",
                buttonImageOnly: false,
                disabled: $("#" + specs.field).attr("disabled"), 
                defaultDate: $("#" + specs.field).val() 
            }, $("#" + specs.field).data('cutomDatepicker'));
            
			$("#" + specs.field).datepicker(params);
		}
		
		function customDatepicker(spec){
			$(spec.selector).data('cutomDatepicker',spec.params);
		
		}
		
		return {
			dateField : init, 
			customDatepicker : customDatepicker
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