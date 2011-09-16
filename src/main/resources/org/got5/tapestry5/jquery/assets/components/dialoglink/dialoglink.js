(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			$('#' + spec.triggerId).click(function() {
	            jQuery('#' + spec.dialogId).dialog('open');
				
				return false;
	        });
		}
		
		return {
			dialogLink : init
		}
	});
	
}) ( jQuery );