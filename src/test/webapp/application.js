(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			//We destroy the jQuery object
			$(".inZoneDialog.ui-dialog-content").dialog("destroy").addClass("toRemove");
			
			//We remove all the DOM
			$(".inZoneDialog.toRemove").remove();
			
		}
		
		return {
			reset : init
		}
	});
	
}) ( jQuery );